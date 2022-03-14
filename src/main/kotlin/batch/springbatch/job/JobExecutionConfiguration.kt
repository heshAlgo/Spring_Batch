package batch.springbatch.job

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Rasung Ki
 */
@Configuration
class JobExecutionConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory["Job"]
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step = stepBuilderFactory["step1"]
        .tasklet {  _: StepContribution, _: ChunkContext ->
            log.info("step1 has executed")

            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step2(): Step = stepBuilderFactory["step1"]
        .tasklet { _: StepContribution, _: ChunkContext ->
            log.info("step2 has executed")

            RepeatStatus.FINISHED
        }.build()

}
