# Restrulz demo

This is an example project for [Restrulz](https://github.com/gantsign/restrulz)
and the [Restrulz Generator](https://github.com/gantsign/restrulz-gen).

**Warning: Restrulz is far from feature complete so this is little more than a proof of concept at this point.**

# Status

This example project enables you to view/edit the example
[people.rrdl](https://github.com/gantsign/restrulz-demo/blob/master/schema/src/main/java/people.rrdl)
REST specification and generate a Swagger specification from it; the Swagger
specification is then used to generate client and server APIs.

# Getting started

1. Install Eclipse

    The following distribution has been tested:
    [http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/neon2](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/neon2)

2. Install the Restrulz Eclipse plugin

    Add the following Eclipse update site: [https://dl.bintray.com/gantsign/eclipse/restrulz/](https://dl.bintray.com/gantsign/eclipse/restrulz/)

    Install the `Restrulz` feature.

3. Clone the project repository:

    ```bash
    git clone https://github.com/gantsign/restrulz-demo.git
    ```

4. Configure access to the GantSign npm repository

    ```bash
    echo '@gantsign:registry=https://api.bintray.com/npm/gantsign/npm/' >> ~/.npmrc
    ```

5. Import the project as a Maven project in Eclipse


6. Build with Maven

    ```bash
    mvn clean install
    ```

# Location of REST specification

The Restrulz specification is located at: `schema/src/main/java/people.rrdl`.

# License

This demo is under the [MIT Licence](https://raw.githubusercontent.com/gantsign/restrulz-demo/master/LICENSE).

# Author Information

John Freeman

GantSign Ltd.
Company No. 06109112 (registered in England)

