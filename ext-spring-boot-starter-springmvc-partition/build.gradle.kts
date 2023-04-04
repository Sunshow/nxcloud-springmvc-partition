dependencies {
    api(project(":springmvc-partition"))
    api(libs.springboot.autoconfigure)

    compileOnly(libs.spring.mvc)
    compileOnly(libs.jackson.databind)
}