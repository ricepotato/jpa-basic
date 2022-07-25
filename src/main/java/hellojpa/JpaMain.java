package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); // entity manager 는 한번 사용하고 버림

        EntityTransaction tx = em.getTransaction();

        tx.begin(); // transaction 시작

        // 여기에 코드 작성
        try{
            Member member = new Member();
            member.setId(3L);
            member.setName("HelloCC"); // 여기까지는 비영속(new/transient) 상태

            em.persist(member); // 여기서 영속(managed) 상태가 됨
            // em.detach(member); // detach 를 실행하면 detach(준영속) 상태가됨 특정 객체를 영속성 컨텍스트에서 제거한다.
            // em.clear(); // 영속성 컨텍스트에 들어있는 모든 객체를 제거한다.

            em.flush(); // 여기서 db 에 query 가 강제로 전달됨. 영속성 컨텍스트에 데이터는 그대로

            Member member1 = em.find(Member.class, 3);
            member1.setName("hello DD");

            tx.commit(); // 여기서 db 에 query 가 전달됨
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        em.close();
        emf.close();

    }
}
