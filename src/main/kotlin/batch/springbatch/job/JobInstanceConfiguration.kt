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

@Configuration
class JobInstanceConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext->

                val contributionJobParameters = contribution.stepExecution.jobExecution.jobParameters
                contributionJobParameters.getString("name")
                contributionJobParameters.getLong("seq")
                contributionJobParameters.getDate("date")
                contributionJobParameters.getDouble("age")

                val chunkJobParameters = chunkContext.stepContext.jobParameters

                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { _, _ ->
                RepeatStatus.FINISHED
            }
            .build()
    }

}
