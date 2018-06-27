package cairoshop.configs;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Startup
@Singleton
@DataSourceDefinition(
        className = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
        name = "java:global/jdbc/shopDS", 
        user = "root",
        password = "root",
        url = "jdbc:mysql://localhost:3306/cairoshop",
        properties = {"res-type=javax.sql.DataSource"}
        
)
public class DataSourceConfig {
        
}
