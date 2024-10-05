**Description**
Plugin aimed to modify project source files: and folders, modify content by setting tokens.

**Goals**
* Rename: renames files and folders recursively
* Replace: replaces content inside files
* ReplaceAndRename: combines Rename and Replace goals

**Parameters**
* _basePath_, mandatory, defines a folder to process
* _tokens_, mandatory, simple tokens, comma-separated list to be replaced in directory/file name and also in files content; format: FROM=TO
* _includes_, comma-separated list of paths to include; must be outside a basePath
* _excludes_, comma-separated list of paths to exclude
* _namingFilter_, regular expression to filter files/folders on name
* _replaceFilter_, list of regex patterns to filter files, similar to one above, both are used in ReplaceAndRename goal; in other goals used separately

**To run**
(after plugin is built and placed to your Maven repository)
mvn <groupId>:RenameReplace:<version>:<goal> -D<params>
