//package batch.springbatch.job
//
//import org.slf4j.LoggerFactory
//import org.springframework.batch.core.Job
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.StepContribution
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
//import org.springframework.batch.core.scope.context.ChunkContext
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//
//@Configuration
//class HelloJobConfiguration(
//    private val jobBuilderFactory: JobBuilderFactory,
//    private val stepBuilderFactory: StepBuilderFactory,
//) {
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    @Bean
//    fun helloJob(): Job {
//        return jobBuilderFactory.get("helloJob")
//            .start(helloStep1())
//            .next(helloStep2())
//            .build()
//    }
//
//    @Bean
//    fun helloStep1(): Step {
//        return stepBuilderFactory.get("helloStep1")
//            .tasklet { _: StepContribution, _: ChunkContext ->
//                log.info("=============")
//                log.info(" >> Hello Spring Batch !!")
//                log.info("=============")
//
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//
//    @Bean
//    fun helloStep2(): Step {
//        return stepBuilderFactory.get("helloStep1")
//            .tasklet { contribution, chunkContext ->
//                log.info("=============")
//                log.info(" >> Start Step2 !!")
//                log.info("=============")
//
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//}
