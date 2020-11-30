package szfm.errorbynight.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void remove() {
    }

    @Test
    void save() {
    }
}
