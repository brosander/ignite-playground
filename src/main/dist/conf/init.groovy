import com.github.brosander.ignite.playground.CacheLoaderFactory
import com.github.brosander.ignite.playground.PlaygroundSecurityManager
import groovy.sql.Sql
import org.apache.ignite.IgniteJdbcThinDriver
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.IgniteConfiguration

System.setSecurityManager(new PlaygroundSecurityManager())
println System.getSecurityManager()

ignite = Ignition.start(new IgniteConfiguration().setClassLoader(getClass().getClassLoader()))

compute = ignite.compute()

exec = ignite.executorService()

expensiveCache = ignite.getOrCreateCache(new CacheConfiguration<String, String>()
        .setName("expensive")
        .setReadThrough(true)
        .setCacheLoaderFactory(new CacheLoaderFactory()))

Class.forName(IgniteJdbcThinDriver.class.getCanonicalName())
sql = Sql.newInstance("jdbc:ignite:thin://127.0.0.1/")

sql.execute '''
  CREATE TABLE City (id LONG PRIMARY KEY, name VARCHAR)
    WITH "template=replicated"
'''

sql.execute'insert into City (id, name) values (?, ?)', [1, 'New York']
sql.execute'insert into City (id, name) values (?, ?)', [2, 'Boston']

sql.execute '''
  CREATE TABLE Person (id LONG, name VARCHAR, city_id LONG, PRIMARY KEY (id, city_id))
    WITH "backups=1, affinityKey=city_id"
'''

sql.execute 'insert into Person (id, name, city_id) values (?, ?, ?)', [1, 'Joe', 1]
sql.execute 'insert into Person (id, name, city_id) values (?, ?, ?)', [2, 'Bob', 2]