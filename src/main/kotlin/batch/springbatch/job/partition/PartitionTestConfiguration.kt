package batch.springbatch.job.partition

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class PartitionTestConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun partitionJob(
        @Qualifier("masterStep") masterStep: Step
    ): Job =
        jobBuilderFactory["partitionJob"]
            .start(masterStep)
            .build()

    @Bean
    fun masterStep(
        @Qualifier("partitioner") partitioner: Partitioner,
        @Qualifier("slaveStep") slaveStep: Step,
        @Qualifier("partitionHandler") partitionHandler: TaskExecutorPartitionHandler
    ): Step {
        return stepBuilderFactory["masterStep"]
            .partitioner("partitioner", partitioner)
            .step(slaveStep)
            .gridSize(GRID_SIZE)
            .partitionHandler(partitionHandler)
            .build()
    }

    @Bean
    fun slaveStep(
        reader: PartitionItemReader,
        writer: PartitionItemWriter,
    ): Step = stepBuilderFactory["slaveStep"]
        .chunk<Customer, Customer>(CHUNK_SIZE)
        .reader(reader)
        .writer(writer)
        .build()

    @Bean
    fun partitionHandler(
        @Qualifier("slaveStep") slaveStep: Step,
    ): TaskExecutorPartitionHandler =
        TaskExecutorPartitionHandler().also {
            it.step = slaveStep
            it.setTaskExecutor(partitionTaskExecutor())
            it.gridSize = POOL_SIZE
        }

    @Bean
    fun partitionTaskExecutor(): TaskExecutor =
        ThreadPoolTaskExecutor().also {
            it.corePoolSize = POOL_SIZE
            it.maxPoolSize = POOL_SIZE
            it.setThreadNamePrefix("partition-thread")
            it.setWaitForTasksToCompleteOnShutdown(true)
            it.setAllowCoreThreadTimeOut(true)
            it.initialize()
        }

    companion object {
        const val CHUNK_SIZE = 5
        const val GRID_SIZE = 3
        const val POOL_SIZE = 3
    }
}
