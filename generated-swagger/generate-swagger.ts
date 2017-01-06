import {SchemaProcessor, SwaggerGenerator} from '@gantsign/restrulz-gen';

const swaggerGenerator = new SwaggerGenerator();

const processor = new SchemaProcessor();
processor.schemaFiles = ['target/rrd/people.rrd.json'];
processor.outputDirectory = 'target/classes';
processor.generators.push(swaggerGenerator);
processor.execute();
