package MyMVC.DAO;

import MyMVC.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> listRole() {
        return entityManager.createQuery("select r from roles r").getResultList();
    }

    @Override
    public void remove(long id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getRole (long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String role) {
        TypedQuery<Role> query = entityManager.createQuery("select r from roles r where r.role =: role", Role.class).
                setParameter("role",role);
        return query.getSingleResult();
    }
}
