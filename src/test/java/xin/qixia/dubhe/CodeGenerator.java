package xin.qixia.dubhe;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class CodeGenerator {

    // 数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/qxiana?useUnicode=true&useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    // 数据库连接驱动
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    // 数据库连接用户名
    private static final String USERNAME = "root";
    // 数据库连接密码
    private static final String PASSWORD = "34707050";
    // 作者名
    private static final String author = "Actrab";
    // 父包名
    private static final String parentPackageName = "com.wkx";
    // 模块名（可选）
    private static final String MODULE_NAME = "qxiana";
    // 项目路径
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + "表名，多个表使用英文逗号分割" + "：");
        if (scanner.hasNext()) {
            String str = scanner.next();
            if (StringUtils.isNotEmpty(str)) {
                return str;
            }
        }
        throw new MybatisPlusException("请输入正确的" + "表名，多个表使用英文逗号分割" + "！");
    }


    public static void main(String[] args) {
        // TODO 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // TODO 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 生成文件的输出目录
        // xxx.java文件放置路径
        String JAVA_PATH = "/src/main/java";
        gc.setOutputDir(PROJECT_PATH + JAVA_PATH);
        // 作者
        gc.setAuthor(author);
        // 是否打开输出目录
        gc.setOpen(false);
        // controller 命名方式，注意 %s 会自动填充表实体属性
        gc.setControllerName("%sController");
        // service 命名方式
        gc.setServiceName("%sService");
        // serviceImpl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // mapper 命名方式
        gc.setMapperName("%sMapper");
        // xml 命名方式
        gc.setXmlName("%sMapper");
        // 开启 swagger2 模式
        gc.setSwagger2(true);
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // 是否开启 ActiveRecord 模式
        gc.setActiveRecord(false);
        // 是否在xml中添加二级缓存配置
        gc.setEnableCache(false);
        // 是否开启 BaseResultMap
        gc.setBaseResultMap(false);
        // XML columnList
        gc.setBaseColumnList(false);
        // 设置生成实体类的日期类型
        gc.setDateType(DateType.ONLY_DATE);
        // 全局 相关配置
        mpg.setGlobalConfig(gc);

        // TODO 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // TODO 包配置
        PackageConfig pc = new PackageConfig();
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(parentPackageName);
        // 模块名，可以不指定
        pc.setModuleName(MODULE_NAME);
        // Controller包名
        pc.setController("controller");
        // Service包名
        pc.setService("service");
        // ServiceImpl包名
        pc.setServiceImpl("service.impl");
        // Mapper 包名
        pc.setMapper("mapper");
        // Entity包名
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        // TODO 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略，驼峰原则
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字数据库表字段映射到实体的命名策略，驼峰原则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 实体是否生成 serialVersionUID
        strategy.setEntitySerialVersionUID(false);
        // 是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 使用lombok
        strategy.setEntityLombokModel(true);
        // 设置逻辑删除键
        strategy.setLogicDeleteFieldName("deleted");
        //自动填充策略
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        //乐观锁
        strategy.setVersionFieldName("version");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        // TODO 指定生成的bean的数据库表名
        strategy.setInclude(scanner().split(","));
        mpg.setStrategy(strategy);

        mpg.execute();
    }

}

