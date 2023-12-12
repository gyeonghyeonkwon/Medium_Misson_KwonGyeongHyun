package com.ll.medium_mission.domain.home.home.Service;

import com.ll.medium_mission.domain.home.home.Entity.MemberUser;
import com.ll.medium_mission.domain.home.home.Entity.Question;
import com.ll.medium_mission.domain.home.home.Repository.QuestionRepository;
import com.ll.medium_mission.global.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 엔티티 DB에 저장
     */
    public void write(String title, String content, MemberUser author) {
       Question q = new Question();
       q.setTitle(title);
       q.setContent(content);
       q.setCreateDate(LocalDateTime.now());
       q.setAuthor(author);
        this.questionRepository.save(q);
    }

    /**
     * 질문 게시글 리스트
     * <p>
     * 질문 DB 테이블 에 저장되어있는 데이터를 조회
     */
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    /**
     * db에 아이디값을 찾을 수 없는 경우 404 예외 발생
     *
     */
    public Question getQuestion(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        }
        throw new NotFoundException("해당하는 게시판을 찾을수없습니다 ");
    }

    /**
     *
     * 수정 내용 저장
     */
    public void modifySave(Question question ,String title, String content ) {
        question.setContent(content);
        question.setTitle(title);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);

    }

    /**
     *
     * 글 삭제
     */
    public void delete(Long id) {

        questionRepository.deleteById(id);

    }
}