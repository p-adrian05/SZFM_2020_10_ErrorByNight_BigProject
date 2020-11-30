package szfm.errorbynight.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.config.TestDataSource;
import szfm.errorbynight.model.Role;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class, TestDataSource.class})
@ActiveProfiles("test")
class RoleDaoImplTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    void findByRole() {
      Role role = roleDao.findByRole("USER").get();
      assertEquals(role.getRole(),"USER");
    }

    @Test
    void add() {
    }

    @Test
    void findById() {
      Role role = roleDao.findByRole("USER").get();
      assertEquals(role.getRole(),roleDao.findById(role.getId()).get().getRole());
    }

    @Test
    void findAll() {
      assertTrue(roleDao.findAll().get().size()>0);
    }
}
