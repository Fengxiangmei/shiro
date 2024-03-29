package cn.fxm.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTests {
//    @Autowired
//    TUserDao tUserDao;
//    @Autowired
//    TUserService tUserService;
//
//    @Transactional
//    @Test
//    public void contextLoads() {
//      TUser tUser =  tUserService.getOne(1);
//      System.out.println(tUser.getUsername());
//    }
// 角色授权、资源授权测试
@Test
public void testAuthorization() {

    // 创建SecurityManager工厂
    IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
            "classpath:shiro-realm.ini");

    // 创建SecurityManager
    SecurityManager securityManager = factory.getInstance();

    // 将SecurityManager设置到系统运行环境，和spring后将SecurityManager配置spring容器中，一般单例管理
    SecurityUtils.setSecurityManager(securityManager);

    // 创建subject
    Subject subject = SecurityUtils.getSubject();

    // 创建token令牌
    UsernamePasswordToken token = new UsernamePasswordToken("zhang",
            "123");

    // 执行认证
    try {
        subject.login(token);
    } catch (AuthenticationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    System.out.println("认证状态：" + subject.isAuthenticated());
    // 认证通过后执行授权

    // 基于角色的授权
    // hasRole传入角色标识
    boolean ishasRole = subject.hasRole("role1");
    System.out.println("单个角色判断" + ishasRole);
    // hasAllRoles是否拥有多个角色
    boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1",
            "role2", "role3"));
    System.out.println("多个角色判断" + hasAllRoles);

    // 使用check方法进行授权，如果授权不通过会抛出异常
    // subject.checkRole("role13");

    // 基于资源的授权
    // isPermitted传入权限标识符
    boolean isPermitted = subject.isPermitted("user:create:1");
    System.out.println("单个权限判断" + isPermitted);

    boolean isPermittedAll = subject.isPermittedAll("user:create:1",
            "user:delete");
    System.out.println("多个权限判断" + isPermittedAll);
    // 使用check方法进行授权，如果授权不通过会抛出异常
    subject.checkPermission("items:create:1");
    subject.checkPermission("user:create:1");
}
}
