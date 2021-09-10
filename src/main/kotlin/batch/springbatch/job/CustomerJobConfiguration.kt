package batch.springbatch.job

import batch.springbatch.model.Customer
import batch.springbatch.reader.CustomerItemReader
import batch.springbatch.writer.CustomerItemWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * @author Rasung Ki
 */
@Configuration
class CustomerJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun simpleJob(
        @Qualifier("stepManager") stepManager: Step,
//        @Qualifier("itemTestStep") itemTestStep: Step,
    ): Job {
        return jobBuilderFactory.get("customerJob")
            .start(parameterStep())
            .next(stepManager)
            .build()
    }

    @Bean
    fun parameterStep(): Step {
        return stepBuilderFactory.get("parameterStep")
            .tasklet { _: StepContribution, chunkContext: ChunkContext ->
                chunkContext.stepContext.jobParameters["number"]
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun itemTestStep(): Step {
        return stepBuilderFactory.get("itemTestStep")
            .chunk<Customer, Customer>(CHUNK_SIZE)
            .reader(CustomerItemReader())
            .writer(CustomerItemWriter())
            .build()
    }

    @Bean
    fun stepManager(
        @Qualifier("partitioner") partitioner: Partitioner,
    ): Step {
        return stepBuilderFactory["stepManager"]
            .partitioner("itemTestStep", partitioner)
            .partitionHandler(partitionHandler())
            .build()
    }

    @Bean
    fun partitionHandler(): TaskExecutorPartitionHandler {
        val partitionHandler = TaskExecutorPartitionHandler()
        partitionHandler.step = itemTestStep()
        partitionHandler.setTaskExecutor(taskExecutor())
        partitionHandler.gridSize = POOL_SIZE
        return partitionHandler
    }

    @Bean
    fun taskExecutor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = POOL_SIZE
        executor.maxPoolSize = POOL_SIZE
        executor.setThreadNamePrefix("partition-thread")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        executor.setAllowCoreThreadTimeOut(true)
        return executor
    }

    companion object {
        const val POOL_SIZE = 5
        const val CHUNK_SIZE = 2
    }
}
