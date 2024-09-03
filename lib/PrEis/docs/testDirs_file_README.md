# TEST DIRECTORIES (DIRPATHS AND FILEPATHS) TEMPLATE FILE

## General Info

* **Version:** `09/02/24`
* **Author:** Steven Eiselen <https://seiselen.github.io/>

### Description (This Document)

Describes and provides specification for the `testDirs.json` file; which is not
included with the `PrEis` install: ergo must be created by end users aaccording
to the *specs-&-reqs* detailed within this document. 

### Description (`testDirs.json` File)

Defines resources (and/xor locations thereof) required to test utils and objects
encompassing ᴀᴋᴀ involved with: file system access (and CRUD), multimedia files,
and externally defined test cases (and/or example data thereof). This includes
(but is not limited to) the following:
* **Filepaths** *(including files within sketch/project `/data` directory)*
* **Dirpaths** *(i.e. directories upon which filesystem utils are tested)*
* **Multimedia Assets** *(defined via filepaths, WLOG)*
* **Textual Test Cases** *(i.e. various extractable test inputs)*
* **Test Case Specs** *(i.e. various extractable test case definitions)*

This file is used for **Testing Purposes Only** via the built-in test code; i.e.
it is **NOT** required when consuming `PrEis` for utility with a sketch/project.
For that matter: you can realize your own testing code if/as desired (and if it 
is better than mine and within a branch: I might kindly ask for a pull request
to `master`!)

### Disclaimer (Docu Maintenance/Updates)

**Check The 'Version' Date** located at the top of this document. The amount of
time between it and when you read this, alongside the same for commit activity: 
is a decent heuristic for how well Steven (i.e. myself) maintained this docu. I
will make efforts to routinely maintain this and the other docu, but such is not
guaranteed; especially as PrEis is still a WIP.


+-[ TUTORIAL ]------------------------------------------------------------------
|# REQUIREMENTS
|  > Processing4 Editor)
|# ENVIRONMENT
|  > PrEis assumes that you are using the Processing4 editor: either standalone,
|    else while using another IDE (e.g. VSCode). If/when I write a [more robust]
|    README with general instructions for using PrEis: check that for more info
|    on how I implement and run PrEis with Processing, including the test code.
|# INSTRUCTIONS
|  1) Create a file within your Processing sketch's `"data/"` directory with the
|     following name: `"testDirs.json"` (which uncoincidentally is the same name
|     of this file, sans the `".template"` part.) 
|  2) If you are using VSCode, make sure that this is a "JSON" file and **NOT**
|     a "JSON With Comments" file. You'll note that this template happens to be
|     the latter type, which should be indicated in the bottom right corner of
|     the VSCode app window.
|  3) If using this file (or a copy thereof) in VSCode, you will also notice a
|     bunch of errors appear after the prior step; as comments of any type are
|     ILLEGAL in 'vanilla' JSON files. Thus you WILL need to remove all comments
|     before using the file with the testbed app; as Processing is not expecting
|     (nor is capable of handling) "JSON with Comments" files.
|  4) See the template code below for all further instructions...
+=============================================================================*/
{
  /*----------------------------------------------------------------------------
  |> (OPTIONAL) This defines the background image used with the GUI testbed. You
  |  may omit this entry if/as desired, as the app will ignore the eventual null
  |  value for the PImage if an image was specified.
  +---------------------------------------------------------------------------*/
  "GUI_TESTBED_BG_IMG" : "data/assets/PrEisGUI_diagnosticGrid.png",

  
  "EXAMPLE_DIRS" : [
    "C:/Games/Doom/WADs/_project_brutality/pb_(READ ONLY)/SPRITES/WEAPONS/Slot_2/smg/Respect",
    "C:/Users/Phoenix/Documents/sounds/CnC_RA_sounds",
    "C:/Users/Phoenix/Documents/WAD music/ALT",
    "C:/Games/Doom/WADs/180_minutes_pour_vivre",
    "C:/Games/Doom/WADs/plutonia_2", 
    "C:/Games/Doom/WADs/epic_2"
  ]
}

## Don't forget to mention handling `.vscode/` and contents thereof!