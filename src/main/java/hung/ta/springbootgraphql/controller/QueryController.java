package hung.ta.springbootgraphql.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import hung.ta.springbootgraphql.datafetcher.AllPeopleDataFetcher;
import hung.ta.springbootgraphql.datafetcher.PersonDataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static graphql.GraphQL.newGraphQL;

@RestController
public class QueryController {
    private final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

    @Value("classpath:person.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @Autowired
    private AllPeopleDataFetcher allPeopleDataFetcher;

    @Autowired
    private PersonDataFetcher personDataFetcher;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWriting();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWriting() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWriting -> typeWriting
                        .dataFetcher("allPeople", allPeopleDataFetcher)
                        .dataFetcher("person", personDataFetcher))
                .build();
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity query(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        LOGGER.info(String.valueOf(result.getErrors()));
        return ResponseEntity.ok(result.getData());
    }

}
