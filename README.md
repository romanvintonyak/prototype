# prototype

To start working with prototype follow steps below:
1. Create new Eclipse workspace and pull the project (including submodules) from GitHub exactly to the folder ${hybrishome}/bin/custom, without creating new folder 'prototye';
2. Import the following hybris' modules to the workspace: admincochpit, backoffice, basecommerce, cms2, cockpit, commercefacades, commerceservices, config, customerreview, hmc, mcc, payment, platform, platformhmc, promotions, solrfacetsearch, solrfacetsearchhmc, ticketsystem, voucher;
3. Add to your ${hybrishome}/config/localextentions.xml the extentions listed in the [` localextensions.xml`](https://github.com/sanabramov/prototype/blob/master/utils/config/localextensions.xml);
4. Configure build path for 5 prototype's extentions: add extentions, listed in project's ${hybrishome}/bin/custom/${projectName}/extensioninfo.xml to required projects in its build path;
5. Check build path of all of the hybris' imported modules: ensure that all required projects are importd and that 'platform' project is present in required projects;
6. Ensure that you use jdk 1.8 for all your projects and change file ${hybrishome}/bin/platform/resources/advanced.properties: set 'build.target=1.8' in line 38;
7. Refresh all projects in workspace and perfofrm 'ant all' from console or IDE view;
8. Perform migration to MySQL, described [here](https://github.com/sanabramov/prototype/wiki/Migration-to-MySQL).

Useful tips and workarounds can be found at our [Wiki](https://github.com/sanabramov/prototype/wiki).
