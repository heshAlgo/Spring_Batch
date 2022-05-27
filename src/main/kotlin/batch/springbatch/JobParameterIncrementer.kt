package batch.springbatch

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import java.time.LocalDateTime

/**
 * @author Rasung Ki
 */
class JobParameterIncrementer : RunIdIncrementer() {
    override fun getNext(parameters: JobParameters?): JobParameters =
        JobParametersBuilder()
            .addString("run.id", LocalDateTime.now().toString())
            .toJobParameters()
}
