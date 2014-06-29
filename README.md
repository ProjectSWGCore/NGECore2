NGECore2
======== 

The ProjectSWG Open Source Project's aim is to create a server emulator for the sandbox MMO Star Wars Galaxies at its final publish before
shutdown on 15/12/2011.

The Open Source Version of ProjectSWG is restricted to 30 minutes uptime and upto 5 connections to ensure that the community
is not split between several small servers. This restriction will not be lifted, it is also not legal to remove this restriction for anyone(see the engine's license for more legal information).

The Engine(NGEngine) is a closed source library and is only licensed for use with this project. The Core(NGECore2) is licensed under the L-GPL License.

Requirements for Building the Core:
======== 

- JDK 8
- A Java 8 compatible IDE like Eclipse Kepler (https://wiki.eclipse.org/JDT/Eclipse_Java_8_Support_For_Kepler)
- A valid Star Wars Galaxies Installation with the final patch
- Postgresql server (www.postgresql.org)
- TRE Explorer(http://forum.modsource.org/index.php?PHPSESSID=bf02fd8244123807f4716c1686abb59f&action=dlattach;topic=33.0;attach=49)
- Github account
- (optional for windows) Github for Windows(http://windows.github.com/)

Once you have met the requirements, fork the project(click on the fork button) and then clone your forked repository.
If you are using Windows then you can clone the repository with Github for Windows, otherwise you can use the following git command:

    git clone https://github.com/your_account_name/ngecore2.git

Then you can import the project to Eclipse.

Next you need to create a postgres DB and restore the nge.backup file. Once you have done that, edit your nge.cfg with your DB information and create an account for yourself in the accounts table of your database.

Once you've done that open TRE Explorer and open the sku0_client.toc in your SWG folder with it.

Then export the following folders to your clientdata folder(should be located in your project folder):
- abstract
- appearance
- creation
- customization
- datatables
- footprint
- interiorlayout
- misc
- object
- quest
- snapshot
- string
- terrain

Export the same folders from sku1_client.toc, sku2_client.toc and sku3_client.toc to avoid errors with kashyyyk.

Now you're ready to run the core!

Contributing and Submitting patches
========

To contribute, simply commit your changes to your fork of the project and then submit a pull request here:

    https://github.com/ProjectSWGCore/NGECore2/compare/
    
Now your changes will be reviewed by other developers and once the changes are approved your code will be merged into the main repository.

For more information please visit the wiki: https://github.com/ProjectSWGCore/NGECore2/wiki

Documentation can be found here: http://projectswgcore.github.io/NGECore2/doc/

Please prefix all commits with Added, Changed, Removed or Fixed. If you don't have enough room for multiple changes, use the extended description. Try not to bundle multiple changes into one vague line (ie. "Changed various combat things"). Try to make all commit messages understandable by non-programmers.
