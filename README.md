# DialogShard
An alternative to DialogFragment, that's intended to overcome fragments exceptions

It has the advantages of a DialogFragment, without the disadvantages of it.

Why use it?
-----------
Because using DialogFragment introduces weird exceptions that can crash your app, even in cases that a normal Dialog should be safe to be shown:

 - https://code.google.com/p/android/issues/detail?id=173117
 - https://code.google.com/p/android/issues/detail?id=207269
 - https://code.google.com/p/android/issues/detail?id=224878

And, you can use it instead of a normal Dialog, if you wish to have the extra benefits of using Fragments.

How to use?
-----------
Import on gradle using:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
	
	dependencies {
	        compile 'com.github.AndroidDeveloperLB:DialogShard:#'
	}

Where # is the number of the release, as shown on jitpack:
https://jitpack.io/#AndroidDeveloperLB/DialogShard/

In code, extend from DialogShard and provide the dialog via onCreateDialog, similar to how you use DialogFragment. 
Note that you can have any kind of CTOR. No need for default one.

When showing the dialog, you have a choice whether you wish it to re-show itself upon configuration change (like orientation change) or not.

You can try out the sample, and change its code, to learn how to use it.
