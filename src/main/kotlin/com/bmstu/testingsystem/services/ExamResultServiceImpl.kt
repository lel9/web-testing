package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.form_data.UserAnswers
import com.bmstu.testingsystem.repositiry.ExamResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("examResultService")
class ExamResultServiceImpl : ExamResultService {

    @Autowired
    private lateinit var examResultRepository: ExamResultRepository

    override fun passTest(exam: Exam, user: User, userAnswers: UserAnswers): ExamResult {
        if (exam.status != ExamStatus.APPROVED)
            throw IllegalStateException()
        val resultString = userAnswers.list?.map { mua ->
            mua?.let { ua ->
                val mq = exam.questions.find { q -> q.id == ua.questionId  }
                mq?.let { q ->
                    if (q.type == QuestionType.NO_ANSWER) {
                        if (ua.checkedVariants != null)
                            throw IllegalStateException()
                        return@map q.correctInputAnswer?.toLowerCase() == ua.inputAnswer?.toLowerCase()
                    }
                    if (ua.inputAnswer != null)
                        throw IllegalStateException()
                    return@map q.correctVariantsId?.toSet() == ua.checkedVariants?.toSet()
                }
            }
        } ?. filter { it == true } ?. count().toString() + "/" + exam.questions.count().toString()

        val result = ExamResult(resultString, java.sql.Date(System.currentTimeMillis()), exam.id, user.id)
        examResultRepository.save(result)
        return result
    }
}
