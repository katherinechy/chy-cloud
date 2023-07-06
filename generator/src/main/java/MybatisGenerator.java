import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;

/**
 * @author katherine
 * @desc
 * @date 2023-07-06  22:06
 */
public class MybatisGenerator {
    public static void main(String[] args) {
        //====================配置变量区域=====================//
        String author = "chy";
        //生成的entity、controller、service等包所在的公共上一级包路径全限定名
        String rootPackage = "com.chy.system.common";
        String moduleName = "common/common-service";
        //数据库配置
        String url = "jdbc:mysql://xxx:3306/common?useSSL=false&characterEncoding=utf8";
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String username = "chy";
        String password = "123456";
        //表名，多个使用,分隔
        String[] tableNames = {"system_dict", "system_user", "system_role", "system_privilege", "system_user_role", "system_role_privilege"};
        //====================配置变量区域=====================//

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/" + moduleName + "/src/main/java");
        //是否覆盖已有文件，默认false
        globalConfig.setFileOverride(true);
        //是否打开输出目录
        globalConfig.setOpen(false);
        globalConfig.setAuthor(author);
        //去掉service接口的首字母I
        globalConfig.setServiceName("%sService");
        //开启 BaseResultMap
        globalConfig.setBaseResultMap(true);
        //只使用 java.util.date代替
        globalConfig.setDateType(DateType.ONLY_DATE);
        //分配ID (主键类型为number或string）
        globalConfig.setIdType(IdType.ASSIGN_ID);

        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName(driverClassName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);

        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(rootPackage);
        generator.setPackageInfo(packageConfig);
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");

        packageConfig.setXml("mapper");
        packageConfig.setEntity("model.po");

        //注意：模板引擎在mybatisplus依赖中的templates目录下，可以依照此默认模板进行自定义

        // 策略配置：配置根据哪张表生成代码
        StrategyConfig strategy = new StrategyConfig();

//        strategy.setInclude("system_dict","system_user","system_role","system_privilege","system_user_role","system_role_privilege");//表名，多个英文逗号分割（与exclude二选一配置）
        //表名，多个英文逗号分割（与exclude二选一配置）
        strategy.setInclude(tableNames);

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.chy.system.base.entity.BasePo");
        strategy.setSuperServiceImplClass(ServiceImpl.class);
       // 移除表前缀
        strategy.setTablePrefix("system_");
        //6、自动填充
        TableFill createTime = new TableFill("created_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("updated_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(createTime);
        list.add(updateTime);
        strategy.setTableFillList(list);

        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        //lombok模型， @  Accessors(chain = true)setter链式操作
        strategy.setEntityLombokModel(true);
        //controller生成@RestController
        strategy.setRestControllerStyle(true);
        //是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        generator.execute();
    }
}
