**Description**
This plugin is aimed to proceed with sync of Maven structure (pom.xml files) of project with some templates set. 
As it is modifying pom.xml files of project during processing, it should be called outside project build process.
Operation of plugin is based on generation of pom.xml files from templates, defined as Java classes which replace then existing files.

**Goals**
UpdateStructure

**Parameters**
* _propertiesFile_: defines a path to a file with project properties, dependencies, modules(repo.properties). If path is not specified then it is defaulted to a current run folder
* _projectPath_: defines a path to a project being updated. If not specified, then it is defaulted to a current run folder. 

**To run**
(after plugin is built and placed to your Maven repository)
mvn <groupId>:TemplatePlugin:<version>:UpdateStructure -D<params>

**Some tips on how to add/amend things**
* All or almost all main components of pom.xml file structure (project, modules, dependencies, properties) have related Java class within _main.java.myexperiments.plugins.maven.xml.components_ package of plugin structure. To simplify a structure almost all components classes have only default and full constructors, where unnecessary parameters are called with _null_
* Properties for **root** pom.xml are _updated_ but for other modules - _overwitten_. This means that when defining module-related things, they should be defined completely
* Properties and dependencies could be added in Profile section - to improve a flexibility of setting up
* Root pom.xml is set to appear as "module_01"

**Properties file(repo.properties**
Format
* module_<xx>.<profile>.<module type> = <path to module>
    _xx_ 2-digit module id(01-99), the same module in different profiles should have the same number, 01 - is always for Root type
    _profile_ if present with "alt" or "default" then module is placed in related profile, if absent, then placed in Modules section of root pom.xml
    _module type_ Java class (template) is used to generate this module
    _path to module_ relative path to a target module
* dependency = <groupId>:<artifactId>:<version>:<scope>:<type>:<classifier>
    _groupId_ and _artifactId_ are mandatory, if other params are absent then "-" is expected there instead
* property.module_<xx>.<property name> = <property value>
    _xx_ 2-digit module id(01-99), the same module in different profiles should have the same number
    _property name_ name of property how it is seen in pom.xml
    _property value_ value of related parameter
* profile_<profile id>.property.<property name> = <property value>
* profile_<profile id>.dependency = <groupId>:<artifactId>:<version>:<scope>:<type>:<classifier>
    _profile id_ profile where property or dependency to be placed
**********
Example
//modules
module_01.Root=.
module_02.SomeModule=Modules/SomeModule
module_03.AnotherModule=Packages/AnotherModule
//dependencies
dependency=dependency.group.id.01:dependency.artifact.id01:1.0.0:provided:-:-
dependency=dependency.group.id.02:dependency.artifact.id02:2.0.0:provided:-:-
dependency=dependency.group.id.03:dependency.artifact.id03:${dep03.version}:provided:-:-
//properties
property.module_01.dep03.version=3.0.0
proile_alt.property.dep03.version=1.2.3
property.module_03.someParam=somevalue
**********
