# 代码自动生成器（code-generator）

Mybatis是目前主流的ORM框架，相比于hibernate的全自动，它是半自动化需要手写sql语句、接口、实体对象，后来推出的Generator自动生成代码，可以帮我们提高开发效率。

使用 Mybatis Generator，可以自动在dao、entity、mapper包下生成代码。

更多说明，可以参考 https://blog.csdn.net/joovor/article/details/105102992 这篇文章。


Mybatis是目前主流的ORM框架，相比于hibernate的全自动，它是半自动化需要手写sql语句、接口、实体对象，后来推出的Generator自动生成代码，可以帮我们提高开发效率。

#### 1.创建SpringBoot项目

File→New→Project… 选择Spring Initializr，选择JDK版本，默认初始化URL![图片1](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZlFkcy5wbmc?x-oss-process=image/format,png)

填写项目名称，java版本，其他描述信息

![图片2](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZnNwQy5wbmc?x-oss-process=image/format,png)

选择web、mybatis、mysql依赖

![图片3](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZmVGUC5wbmc?x-oss-process=image/format,png)

选择项目存放路径

![图片4](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZnZvaS5wbmc?x-oss-process=image/format,png)

Next–>Finish完成项目创建

#### 2. mybatis-generator-maven插件的配置

打开项目的pom.xml文件添加

```
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
</plugin>
```


#### 3. 项目结构构建

在项目目录下(这里是mybatis)添加controller、service、dao、entity包，在resources下添加mapper包存放映射文件。

![图片5](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZk12TC5wbmc?x-oss-process=image/format,png)

#### 4. application.yml配置

```

#端口号配置
server:
  port: 8088
spring:
  #模板引擎配置
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    mode: HTML
    encoding: UTF-8
    cache: false
    servlet:
      content-type: text/html
  #静态文件配置
  resources:
    static-locations: classpath:/static,classpath:/META-INF/resources,classpath:/templates/
  #jdbc配置
  datasource:
    url: jdbc:mysql://localhost:3306/tube?useUnicode=true&characterEncoding=utf8
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
#mybatis配置
mybatis:
  #映射文件路径
  mapper-locations: classpath:mapper/*.xml
  #模型所在的保命
  type-aliases-package: com.pieruo.springboot.entity
```

#### 5. generatorConfig.xml配置

在resources文件下创建generatorConfig.xml文件，配置如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<!-- 配置生成器 -->
<generatorConfiguration>

<!--classPathEntry:数据库的JDBC驱动,换成你自己的驱动位置 可选 -->
<classPathEntry location="/Users/kefaming/Documents/work/mavenlib/mysql/mysql-connector-java/5.1.37/mysql-connector-java-5.1.37.jar"/>

<!-- 一个数据库一个context,defaultModelType="flat" 大数据字段，不分表 -->
<context id="MysqlTables" targetRuntime="MyBatis3Simple" defaultModelType="flat">

    <!-- 自动识别数据库关键字，默认false，如果设置为true，根据SqlReservedWords中定义的关键字列表；一般保留默认值，遇到数据库关键字（Java关键字），使用columnOverride覆盖 -->
    <property name="autoDelimitKeywords" value="true"/>

    <!-- 生成的Java文件的编码 -->
    <property name="javaFileEncoding" value="utf-8"/>

    <!-- beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号； -->
    <property name="beginningDelimiter" value="`"/>
    <property name="endingDelimiter" value="`"/>

    <!-- 格式化java代码 -->
    <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>

    <!-- 格式化XML代码 -->
    <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>
    <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
    <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>

    <!-- 注释 -->
    <commentGenerator>
        <property name="suppressAllComments" value="true"/><!-- 是否取消注释 -->
        <property name="suppressDate" value="false"/> <!-- 是否生成注释代时间戳-->
    </commentGenerator>

    <!-- jdbc连接-->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                    connectionURL="jdbc:mysql://localhost:3306/db2?serverTimezone=UTC" userId="root"
                    password="root"/>

    <!-- 类型转换 -->
    <javaTypeResolver>
        <!-- 是否使用bigDecimal， false可自动转化以下类型（Long, Integer, Short, etc.） -->
        <property name="forceBigDecimals" value="false"/>
    </javaTypeResolver>

    <!-- 生成实体类地址 -->
    <javaModelGenerator targetPackage="com.test.generator.entity" targetProject="src/main/java">
        <!-- 是否让schema作为包的后缀 -->
        <property name="enableSubPackages" value="false"/>
        <!-- 从数据库返回的值去掉前后空格 -->
        <property name="trimStrings" value="true"/>
    </javaModelGenerator>

    <!-- 生成map.xml文件存放地址 -->
    <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
        <property name="enableSubPackages" value="false"/>
    </sqlMapGenerator>

    <!-- 生成接口dao -->
    <javaClientGenerator targetPackage="com.test.generator.dao" targetProject="src/main/java" type="XMLMAPPER">
        <property name="enableSubPackages" value="false"/>
    </javaClientGenerator>

    <!-- table可以有多个,每个数据库中的表都可以写一个table，tableName表示要匹配的数据库表,也可以在tableName属性中通过使用%通配符来匹配所有数据库表,只有匹配的表才会自动生成文件 enableSelectByPrimaryKey相应的配置表示是否生成相应的接口 -->
    <table tableName="stock" enableCountByExample="false" enableUpdateByExample="false"
           enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"
           enableSelectByPrimaryKey="true" enableUpdateByPrimaryKey="true"
           enableDeleteByPrimaryKey="true">
        <property name="useActualColumnNames" value="true"/>
    </table>

</context>
</generatorConfiguration>
```

#### 6.添加启动项

选择Edit Configuration… 点击加号"+"添加，选择maven，填写名称(这里用mybatis generator)，命令行：mybatis-generator:generate -e

![图片6](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZk4xai5wbmc?x-oss-process=image/format,png)


#### 7.启动

选择 Mybatis Generator 启动，自动在dao、entity、mapper包下生成代码

![图片7](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4ua2VmYW1pbmcuY29tLzIwMjAvMDMvMjQvZlJQSi5wbmc?x-oss-process=image/format,png)

