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
class BasicConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory.get("simpleJob")
            .start(simpleStep())
            .build()
    }

    @Bean
    fun simpleStep(): Step {
        return stepBuilderFactory.get("simpleStep")
            // Tasklet을 구현하면 스텝이 중지 될때까지 execute 메서드가 계속 수행
            // StepContribution : 아직 커밋되지 않은 현재 트랜잭션에 대한 정보를 가지고 있다.
            // ChunkContext : 실행 시점의 잡 상태를 제공 및 tasklet 내에서는 처리중인 청크와 관련된 정보(스텝 및 잡)도 가지고 있다.
            .tasklet { _: StepContribution, _: ChunkContext ->
                println("Hello World")
                RepeatStatus.FINISHED
            }.build()
    }

}
