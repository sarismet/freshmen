package main;

import java.util.*;
import java.io.*;
import java.net.*;

import project.MerkleTree;

public class Main {

	public static void main(String[] args) {

		// MerkleTree m0 = new MerkleTree("sample/white_walker.txt");
		MerkleTree m0 = new MerkleTree("data/1meta.txt");
		// MerkleTree m0 = new MerkleTree("data/1.txt");
		// MerkleTree m0 = new MerkleTree("sample/white_walkermeta.txt");
		//String hash = m0.getRoot().getLeft().getRight().getData();
		System.out.println(hash);

		// boolean valid = m0.checkAuthenticity("sample/white_walkermeta.txt");
		boolean valid = m0.checkAuthenticity("data/1meta.txt");
		System.out.println(valid);

		// The following just is an example for you to see the usage.
		// Although there is none in reality, assume that there are two corrupt chunks
		// in this example.
		// ArrayList<Stack<String>> corrupts = m0.findCorruptChunks("data/0meta.txt");
		ArrayList<Stack<String>> corrupts = m0.findCorruptChunks("data/1meta.txt");
		System.out.println("Corrupt hash of first corrupt chunk is: " + corrupts.get(0).pop());
		// System.out.println("Corrupt hash of second corrupt chunk is: " +
		// corrupts.get(1).pop());

		download("secondaryPart/data/download_from_trusted.txt");

	}

	public static void download(String path) {
		// Entry point for the secondary part
		URL url = null;

		try {

			File file = new File(path);
			Scanner concole = new Scanner(file);
			int index = 4;
			String[] str = new String[3];
			str[0] = "meta";
			str[1] = "";
			str[2] = "alt";
			int add = 0;
			while (concole.hasNextLine()) {
				String line = concole.nextLine();

				if (line.equals("")) {
					index++;
					add = 0;
				}

				if (!line.equals("")) {

					url = new URL(line);
					URLConnection connection = url.openConnection();
					InputStream in = connection.getInputStream();

					String filename = "secondaryPart/data/" + String.valueOf(index) + str[add];

					FileOutputStream fos = new FileOutputStream(new File(filename + ".txt"));

					byte[] buf = new byte[4096];
					while (true) {
						int len = in.read(buf);
						if (len == -1) {
							break;
						}
						fos.write(buf, 0, len);
					}
					in.close();
					fos.flush();
					fos.close();
					add++;
					if (add == 3) {
						add = 0;
					}
				}

			}

			String[] second = new String[2];
			second[0] = "";
			second[1] = "alt";

			int fileintdex = 4;

			for (int i = 0; i < 5; i++) {

				for (int j = 0; j < 2; j++) {
					Scanner concolex = new Scanner(new File("secondaryPart/data/" + fileintdex + second[j] + ".txt"));

					int index3 = 0;

					PrintStream stream = new PrintStream(
							new File("secondaryPart/data/" + fileintdex + second[j] + "data.txt"));
					while (concolex.hasNextLine()) {
						String line = concolex.nextLine();

						url = new URL(line);
						URLConnection connection = url.openConnection();
						InputStream in = connection.getInputStream();

						byte[] buf = new byte[4096];

						FileOutputStream fos = null;
						while (true) {
							int len = in.read(buf);

							if (len == -1) {
								break;
							}

							fos = new FileOutputStream(
									new File("secondaryPart/data/" + fileintdex + second[j] + index3));
							fos.write(buf, 0, len);

							stream.println("secondaryPart/data/" + fileintdex + second[j] + index3);

							index3++;
						}

						in.close();
						fos.flush();
						fos.close();

					}
					concolex.close();

				}

				MerkleTree m0 = new MerkleTree("secondaryPart/data/" + fileintdex + second[0] + "data.txt");
				String hash = m0.getRoot().getLeft().getRight().getData();
				System.out.println(hash);
				System.out.println("Root Value  :  "+m0.getRoot().getData());
				boolean valid = m0.checkAuthenticity("secondaryPart/data/" + fileintdex + "meta.txt");
				System.out.println(valid);
				if (!valid) {
					ArrayList<Stack<String>> corrupts = m0
							.findCorruptChunks("secondaryPart/data/" + fileintdex + "meta.txt");
					System.out.println("Corrupt hash of first corrupt chunk is: " + corrupts.get(0).pop());

					m0 = new MerkleTree("secondaryPart/data/" + fileintdex + second[1] + "data.txt");
					hash = m0.getRoot().getLeft().getRight().getData();
					System.out.println(hash);
					System.out.println("Root Value after making it correct  :  "+m0.getRoot().getData());

					valid = m0.checkAuthenticity("secondaryPart/data/" + fileintdex + "meta.txt");

				}

				fileintdex++;

			}

			concole.close();

		} catch (MalformedURLException mue) {

			System.out.println("Ouch - a MalformedURLException happened.");

			mue.printStackTrace();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
