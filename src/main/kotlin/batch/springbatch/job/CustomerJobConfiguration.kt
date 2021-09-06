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
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Rasung Ki
 */
@Configuration
class CustomerJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory.get("customerJob")
            .incrementer(RunIdIncrementer())
            .start(parameterStep())
            .next(itemTestStep())
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
            .chunk<Customer, Customer>(2)
            .reader(CustomerItemReader())
            .writer(CustomerItemWriter())
            .build()
    }

}
