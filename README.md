NPC-Lib
=======

This a proof-of-concept NPC-Library. It makes use of pure reflection to create NPC's and detect 
interaction with them. (trough packet receiving, if protocolLib is present then it will use that,
if not then it will use my own packet interceptor)

I hope I can finish the library soon. Ofcourse, since I made this library as a POC, you can just
copy-paste the code inside your plugin. If you do this then please just download the code, import it,
then change the package name from common.captainbern.npclib to <your_plugin_name>.common.captainbern.npclib. 
I wrote this library for you and you can just copy it inside your plugin so the least you can do is keep my 
name inside the package name ;p .

The Lib itself is also a plugin and therefor also needs to be loaded inside the JVM. You can do this 
by simply adding this inside your main class.

```
NPCLib npcLib;

public void onEnable(){
  npcLib = new NPCLib();
  
  npcLib.onEnable();
}

public void onDisable(){
  npcLib.onDisable();
```

I know it is probably not the best way of doing this but since I guess most people just don't want
to fool around with ClassLoaders I think this will be the easiest one.

How can I add new features and help making this library perfect?
======

Easy! Fork the project, then add the code you want and make a pull request! If I like your code then
I will probably pull it.

So do you have some coding rules?
- Short answer, no but yes.

When creating code it should always look like this:

```
public coolMethod() {

}
```
(the spacing between the () and the { can be removed tho)

but never (jamais, nie, numquam, aldrig) do this:

```
public notSoCoolMethod()
{

}
```

I hate it when code looks like that! Sopmetimes it looks nice but in this case it won't.
Code that is written like this will *not* get pulled.

Also, you can documentate your code. You're free to choose, if you won't, okay then. If you do, cool.
Documentating your code can be either by using /* */ or just // or the real javadoc syntax.

Well thats it for now. Stay tuned and happy coding.
























