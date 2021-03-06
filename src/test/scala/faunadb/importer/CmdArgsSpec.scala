package faunadb.importer

import faunadb.specs._

class CmdArgsSpec extends SimpleSpec {

  "import-file" should "be parsed" in {
    CmdArgs.parse(
      Array(
        "import-file",
        "--class", "data",
        "--secret", "abc",
        "--endpoints", "http://localhost:8443, http://localhost:8444",
        "--batch-size", "2",
        "--concurrent-streams", "10",
        "--error-strategy", "continue",
        "--format", "id:ref, name:string, age:long, oldName->newName:string",
        "--skip-root", "true",
        "--ignore-fields", "a,b,c",
        "--ts-field", "tsField",
        "--report-type",  "silent",
        "--max-network-errors", "10",
        "--reset-network-errors-period", "30",
        "--network-errors-backoff-time", "1",
        "--max-network-errors-backoff-time", "30",
        "--network-errors-backoff-factor", "2",
        "src/test/resources/testdata.json"
      )
    ) shouldNot be(None)
  }

  "import-schema" should "be parsed" in {
    CmdArgs.parse(
      Array(
        "import-schema",
        "--secret", "abc",
        "src/test/resources/schema.yaml"
      )
    ) shouldNot be(None)

    CmdArgs.parse(
      Array(
        "import-schema",
        "--secret", "abc",
        "src/test/resources/min-schema.yaml"
      )
    ) shouldNot be(None)
  }

}
