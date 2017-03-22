import {
  CompositeKotlinGenerator,
  KotlinJsonReaderGenerator,
  KotlinJsonWriterGenerator,
  KotlinModelGenerator,
  KotlinSpringMvcGenerator,
  KotlinValidatorGenerator,
  SchemaProcessor
} from '@gantsign/restrulz-gen';

const compositeGenerator = new CompositeKotlinGenerator();
compositeGenerator.licenseHeader = `\
/*
 * MIT License
 *
 * Copyright (c) 2017 GantSign Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
`;
compositeGenerator.packageMapping['people'] = 'com.example.people';

compositeGenerator.children.push(new KotlinModelGenerator());
compositeGenerator.children.push(new KotlinValidatorGenerator());
compositeGenerator.children.push(new KotlinJsonWriterGenerator());
compositeGenerator.children.push(new KotlinJsonReaderGenerator());
compositeGenerator.children.push(new KotlinSpringMvcGenerator());

const processor = new SchemaProcessor();
processor.schemaFiles = ['target/rrd/people.rrd.json'];
processor.outputDirectory = 'target/generated-sources';
processor.generators.push(compositeGenerator);
processor.execute();
