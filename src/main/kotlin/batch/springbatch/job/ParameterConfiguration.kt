package batch.springbatch.job

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
class ParameterConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun basicJob(): Job {
        return jobBuilderFactory.get("basicJob")
            .start(basicStep())
            .build()
    }

    @Bean
    fun basicStep(): Step {
        return stepBuilderFactory.get("basicStep")
            .tasklet {_: StepContribution, chunkContext: ChunkContext ->
                val name = chunkContext.stepContext.jobParameters["name"]

                println("Hello $name")
                RepeatStatus.FINISHED
            }.build()
    }

}
