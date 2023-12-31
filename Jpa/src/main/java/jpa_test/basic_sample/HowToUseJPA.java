package jpa_test.basic_sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class HowToUseJPA {
    public static void main(String[] args) {
        //엔티티 메니저 팩토리 생성
        //팩토리는 생성 비용이 크고, 쓰레드간 공유가 가능하므로 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 사용해야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2database");

        //엔티티 메니저 생성
        //엔티티 매니저는 생성비용이 적고 공유되어서는 안된다.
        EntityManager em = emf.createEntityManager();

        //트랜잭션 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); //트랜잭션 시작
            logic(em); //비즈니스 로직 실행
            tx.commit(); //트랜잭션 커밋
        } catch (Exception e){
            tx.rollback(); //롤백
        } finally {
            em.close(); //엔티티 메니저 종료
        }
        emf.close(); //엔티티 메니저 팩토리 종료
    }

    public static void logic(EntityManager em){
        String id = "id1";
        User user = new User();
        user.setId(id);
        user.setUsername("이름");
        user.setAge(10);

        // 등록
        em.persist(user);

        // 수정
        user.setAge(20);

        // 한 건 조회
        User findUser = em.find(User.class, id);
        System.out.println(findUser.getId());

        // 목록 조회
        List<User> users = em.createQuery("select m from Principal m", User.class)
                .getResultList();

        // 삭제
        em.remove(user);


    }
}
