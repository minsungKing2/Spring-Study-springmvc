package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    //Test 끝나면, 초기화 해주는 메서드
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test //회원 저장
    void save(){
        //given: 이런게 주어졌을때
        Member member = new Member("hello", 20);

        //when: 이런걸 실행했을때
        Member savedMember = memberRepository.save(member);

        //then: 결과는 이거여야해
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test //전체 회원 조회
    void findAll(){
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }
}
