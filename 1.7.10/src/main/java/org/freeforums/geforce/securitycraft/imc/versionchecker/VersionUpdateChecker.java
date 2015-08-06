package org.freeforums.geforce.securitycraft.imc.versionchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.Loader;
import net.minecraft.nbt.NBTTagCompound;

public class VersionUpdateChecker {

	public static NBTTagCompound getNBTTagCompound() {
		NBTTagCompound tag = new NBTTagCompound();
		Gson gson = new GsonBuilder().create();
				
		try{
			URL updateURL = new URL("https://www.github.com/Geforce132/SecurityCraft/raw/master/Updates/" + Loader.MC_VERSION + ".json");
			BufferedReader in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
		
			Update update = gson.fromJson(in, Update.class);
			
			if(update.getVersion().matches("0.0.0") && update.getFileName().matches("test")){
				mod_SecurityCraft.log("Running the latest version, no new updates avaliable.");
				return null;
			}
			
			tag.setString("newVersion", update.getVersion());
			tag.setString("updateUrl", update.getDownloadURL());
			tag.setString("changelog", update.getChangelog());
			tag.setString("newFileName", update.getFileName());
			tag.setBoolean("isDirectLink", true);
		}catch(JsonSyntaxException e){
			e.printStackTrace();
			return null;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(MalformedURLException e){
			e.printStackTrace();
		    return null;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		
		return tag;
	}
	
public static class Update {
	
	private String version;
	private String downloadURL;
	private String fileName;
	private String changelog;

	public Update(String version, String downloadURL, String fileName, String changelog){
		this.version = version;
		this.downloadURL = downloadURL;
		this.fileName = fileName;
		this.changelog = changelog;
	}
	
	public String getVersion(){
		return version;
	}
	
	public String getDownloadURL(){
		return downloadURL;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getChangelog(){
		return changelog;
	}
	
}

}
