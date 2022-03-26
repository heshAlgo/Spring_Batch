package batch.springbatch.job

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PartitionTestConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun partitionJob(
        @Qualifier("testStep") testStep: Step
    ): Job = jobBuilderFactory["partitionJob"]
        .incrementer(RunIdIncrementer())
        .start(testStep)
        .build()

    @Bean
    fun testStep(): Step {
        return stepBuilderFactory["testStep"].tasklet { contribution, chunkContext ->
            log.info("===== test =====")
            RepeatStatus.FINISHED
        }.build()
    }
}
