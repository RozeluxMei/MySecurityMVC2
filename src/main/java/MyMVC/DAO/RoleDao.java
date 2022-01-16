package MyMVC.DAO;

import MyMVC.model.Role;
import MyMVC.model.User;
import java.util.List;

public interface RoleDao {
    void add(Role role);
    List<Role> listRole();
    void remove(long id);
    void update (Role role);
    Role getRole (long id);
    Role getRoleByName (String role);
}
