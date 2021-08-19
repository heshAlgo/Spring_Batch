package batch.springbatch.test

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
class TestConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("job")
            .start(step())
            .build()
    }

    @Bean
    fun step(): Step {
        return stepBuilderFactory.get("step1")
            // Tasklet을 구현하면 스텝이 중지 될때까지 execute메서드가 계속 수행
            .tasklet { _: StepContribution, _: ChunkContext ->
                println("Hello World")
                RepeatStatus.FINISHED
            }.build()
    }

}
