package com.test.automation.fp.testdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class UpdateTestData {
	
	
	 static String strFeatureTemplate="src//test//resources//features";
	 public List<File> lstAllFiles=new ArrayList<File>();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//delete the existing features folder
		//File existingFold=new File("src//test//resources//features//");
		
		//if(existingFold.exists())
			//FileUtils.deleteDirectory(existingFold);
		
		//check if it is any processing for single file
		for(int j=0; j<args.length; j++){
			if(args[j].contains("features"))
				strFeatureTemplate=args[j];
			
		}
		
		
		UpdateTestData wc=new UpdateTestData();
		//read all files inside the folder
		wc.listFilesForFolder(new File(strFeatureTemplate));

		//loop all the feature files inside the folder
		for(int i=0; i<wc.lstAllFiles.size(); i++){
		
		String UpdatedContent=wc.readFile(wc.lstAllFiles.get(i));
		String writeFileName=wc.lstAllFiles.get(i).getAbsolutePath();//replaceAll("script-template", "features");
		if(wc.lstAllFiles.get(i).exists()){
			boolean blnDeleStatus = wc.lstAllFiles.get(i).delete();
			if(!blnDeleStatus){
				throw new Exception("Unable to delete the file "+wc.lstAllFiles.get(i).getName());
			}
		}
		
		wc.writeFile(writeFileName, UpdatedContent);
		}

	}
	
	/**
	 * Method update the test data from excel
	 * @param file
	 * @param line
	 * @return
	 */
	public String updateData(File file, String line) throws IOException{
		
		CsvDataAccess eD=new CsvDataAccess("src//test//resources//testdata//", "TestData");
		//TODO: write some logic for dynamically getting the test data sheet
		//get the row number
		String fileName=file.getName();
		String fileNameWithoutExt = FilenameUtils.getBaseName(fileName);
		int rowNum=eD.getRowNum(fileNameWithoutExt, 0);
		
		String strDataKeyword=null;
		
		while(line.contains("$")){
			//remove the reference identifier and split by the string
			String nextTrimLine=line.split("\\$")[1];
			strDataKeyword=nextTrimLine.split("\\||\\'|\\r|\\n|\\ ")[0];
			
			//get the column identifier
			line=line.replaceAll("\\$"+strDataKeyword.trim(), eD.getValue(rowNum, strDataKeyword));
			
		}
		
		return line;
	}
	
	/**
	 * Method to read the file
	 * @param file
	 */
	public String readFile(File file){
		StringBuilder stringBuilder = new StringBuilder();
		FileReader fileReader = null;
		try {
			 fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				//update the test data with correct one
				String updatedLine=updateData(file,line);
				stringBuilder.append(updatedLine);
				stringBuilder.append("\n");
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stringBuilder.toString();
		
	}
	
	/**
	 * method to write the folder
	 * @param strWriteContent
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void writeFile(String fileName, String strWriteContent) throws IOException{
		File file = new File(fileName);
		
		File parentDir=file.getParentFile();
		//to check whether dir is exists
		if(!parentDir.exists())
			parentDir.mkdirs();
		
		//to check wther file exists
		if(file.exists())
			file.delete();
		else
			file.createNewFile();
		
		FileUtils.writeStringToFile(file, strWriteContent, false);
	}
	
	
	
	/**
	 * method to return all files
	 * @return list of files
	 */
	public List<File> listFilesForFolder(File folder) {
		
		if(folder.isFile()){
			lstAllFiles.add(folder);
		}else{
		
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	lstAllFiles.add(fileEntry);
	        }
	    }
		}
	    return lstAllFiles;
	}
	
	
	/** code logic for splitiing the word with "space", "break line"
	 * /*char[] ab = line.split("&")[1].toCharArray();
		char[] cd = new char[ab.length];
		int i = 0;
		while (ab.length>i && (ab[i] != ' ' && ab[i] != '|' && ab[i] != '\''))
		{
		    cd[i] = ab[i];
		    i++;
		   // System.out.println(i);
		}
		String word = new String(cd);
		System.out.println(word);*/
		
		
	
	 

}