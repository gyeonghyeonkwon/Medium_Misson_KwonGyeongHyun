package com.ll.medium_mission.domain.home.home.Service;

import com.ll.medium_mission.domain.home.home.Entity.MemberUser;
import com.ll.medium_mission.domain.home.home.Repository.MemberRepository;
import com.ll.medium_mission.domain.home.home.form.MemberUserCreateForm;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private Session entityManager;
    ;

    /**
     *  memberuser DB 저장
     *  비밀번호 암호화하여 db 저장
     *  회원가입시 저장
     */
    public MemberUser create (String username , String nickname ,String password) {

        MemberUser user = new MemberUser();

        user.setUsername(username); //이름

        user.setNickname(nickname); //아이디

        user.setPassword(passwordEncoder.encode(password));

        this.memberRepository.save(user);

        return user;

    }

    /**
     * db에서 저장된 아이디를 찾아
     * 중복된 아이디가 있으면 예외를 발생킨다.
     * joinController 에서 실행
     */
    public void findByNickname (MemberUserCreateForm memberUserCreateForm) {

        Optional<MemberUser> memberUser = memberRepository.findByNickname(memberUserCreateForm.getNickname());

        if (memberUser.isPresent()) {
            throw new IllegalArgumentException("아이디가 존재합니다.");
        }

    }

    /**
     *
     * QuestionModifyController 에서 실행
     */
    public MemberUser getUser(String nickname) {

        Optional<MemberUser> findUser = memberRepository.findByNickname(nickname);

        if (findUser.isPresent()) {
            return findUser.get();
        }
        throw new IllegalArgumentException("아이디가 존재합니다.");
    }

    /**
     * 멤버 아이디
     */
    public MemberUser findById(Long id) {
        Optional<MemberUser> memberUser = this.memberRepository.findById(id);
        if(memberUser.isPresent()){
            return memberUser.get();
        }
            throw new IllegalArgumentException("멤버를 찾을 수 없습니다");
    }

    /**
     * 전체 회원 조회
     */
    public List<MemberUser> findMemberUser() {

        return memberRepository.findAll();
    }

    @Transactional
    public void updateMemberIsPaid(MemberUser memberUser, boolean isPaid) {

        memberUser.setPaid(isPaid);
        memberRepository.save(memberUser);

    }
}



