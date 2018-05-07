package singhr2.examples.mongo.client.SpringbootMongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import singhr2.examples.mongo.client.User;
import singhr2.examples.mongo.repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages="singhr2.examples.mongo.repository")
public class SpringbootMongoClientApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongoClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //repository.save(new User("demo1", "pwd123"));
        //repository.save(new User("demo2", "pwd123"));

        System.out.println("-------------------");

        for(User user: repository.findAll() ){
            System.out.println(">" + user);
        }

        // fetch an individual user
        System.out.println("User found with findByFirstName('demo1'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUsername("demo1"));
    }
}
