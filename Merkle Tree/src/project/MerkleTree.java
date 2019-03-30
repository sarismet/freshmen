package project;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import util.HashGeneration;

public class MerkleTree {
	String path;
	Node root;
	int levelx;
	int numchunk;

	Queue<String> chunk;
	Stack<String> metachunk;
	TreeMap<String, Stack<String>> maincontrol;

	public MerkleTree(String path) {
		this.path = path;
		
		maincontrol = new TreeMap<>();

		try {

			chunk = new LinkedList<>();
			metachunk = new Stack<>();

			chunk = create(chunk);

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public Queue<String> create(Queue<String> chunk) throws NoSuchAlgorithmException, IOException {

		File file = new File(path);
		Scanner concole = new Scanner(file);
		Scanner concole2 = new Scanner(file);
		int levelnum = 0;
		while (concole2.hasNextLine()) {
			levelnum++;
			String chunkdata = HashGeneration.generateSHA256(new File(concole2.nextLine()));

			chunk.add(chunkdata);

		}
		int level = 0;
		this.numchunk = levelnum;
		if (levelnum <= 2) {
			level = 1;
		} else if (levelnum <= 4) {
			level = 2;

		} else if (levelnum <= 8) {
			level = 3;
		}
		int pow = 1;

		while (levelnum / (double) Math.pow(2, pow) > 1) {
			pow++;
		}
		level = pow;
		this.levelx = level;
	
		root = create(root, level, 0, file, concole);
		
		root = assamble(root);

		concole.close();
		concole2.close();
		return chunk;

	}
	
	public void write(Node root,int level) {
		if(root==null) {
			return ;
		}
		
		write(root.left,level+1);
		write(root.right,level+1);
		
	}

	public Node create(Node root, int level, int depth, File file, Scanner concole)
			throws NoSuchAlgorithmException, IOException {

		if (root == null) {
			root = new Node(null);
		}
		if (level == depth) {

			if (concole.hasNextLine()) {
				String pathline = concole.nextLine();
				root = new Node(HashGeneration.generateSHA256(new File(pathline)));
			} else {

				root = new Node(null);
			}

			return root;
		}
		root.left = create(root.left, level, depth + 1, file, concole);
		root.right = create(root.right, level, depth + 1, file, concole);
		return root;

	}

	public Node assamble(Node root) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		if (root.left == null && root.right == null) {

			return root;

		}

		root.left = assamble(root.left);
		root.right = assamble(root.right);

		if (root.left.value != null && root.right.value != null) {

			root.setValue(HashGeneration.generateSHA256(root.left.value + root.right.value));

			return root;
		} else if (root.left.value != null && root.right.value == null) {
			root.setValue(HashGeneration.generateSHA256(root.left.value));
			return root;
		} else if (root.left.value == null && root.right.value != null) {
			root.setValue(HashGeneration.generateSHA256(root.right.value));
			return root;
		} else if (root.left.value == null && root.right.value == null) {

			root.setValue(null);
			return root;
		}

		return root;

	}



	public Node getRoot() {

		return this.root;
	}

	public TreeMap<Integer, Queue<String>> treestack(Node root, TreeMap<Integer, Queue<String>> map, int level,
			int depth) {
		if (root == null) {
			return null;
		}
		if (!map.containsKey(depth)) {
			Queue<String> stack = new LinkedList<>();
			if (root.value != null) {
				stack.add(root.value);
				map.put(depth, stack);
			}

		} else if (map.containsKey(depth)) {
			Queue<String> stack = map.get(depth);
			if (root.value != null) {
				stack.add(root.value);
				map.replace(depth, stack);
			}

		}
		treestack(root.left, map, level, depth + 1);
		treestack(root.right, map, level, depth + 1);

		return map;
	}

	public boolean checkAuthenticity(String string) {
		try {
			Queue<String> stack = new LinkedList<>();
			Queue<String> stack2 = new LinkedList<>();
			File file = new File(string);
			Scanner concole = new Scanner(file);
			while (concole.hasNextLine()) {
				String line = concole.nextLine();
				stack.add(line);
				this.metachunk.add(line);

			}
			concole.close();
			TreeMap<Integer, Queue<String>> map = new TreeMap<>();
			map = treestack(this.root, map, this.levelx, 0);

			Iterator<Integer> itere = map.keySet().iterator();
			while (itere.hasNext()) {
				Queue<String> temp = map.get(itere.next());
				Iterator<String> itere2 = temp.iterator();
				while (itere2.hasNext()) {
					stack2.add(itere2.next());

				}
			}

			while (!stack2.isEmpty()) {
				String line = stack.poll();
				String line2 = stack2.poll();

				if (!line.equals(line2)) {


					return false;
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return true;
	}

	public Node corruption(Node root, Queue<String> chunk, Queue<String> newmeta) {
		if (root.getValue() != null) {
			if (root.left == null && root.right == null) {

				if (!chunk.isEmpty() && !newmeta.isEmpty()) {
					String cline = chunk.poll();
					String mline = newmeta.poll();

					if (!(cline.equals(mline))) {

						Stack<String> stack = new Stack<>();
						stack.add(cline);
						maincontrol.put(cline, stack);
						root.setCheck(false);

					}

				}
				return root;

			}

			root.left = corruption(root.left, chunk, newmeta);
			if (root.left.isCheck() == false) {

				if (!maincontrol.isEmpty()) {
					Iterator<String> itere = maincontrol.keySet().iterator();
					while (itere.hasNext()) {
						String key = itere.next();
						Stack<String> temp = new Stack<>();
						temp = maincontrol.get(key);
						if (temp.contains(root.left.value)) {
							maincontrol.get(key).add(root.value);

						}
					}
				}
				root.setCheck(false);

			}
			root.right = corruption(root.right, chunk, newmeta);
			if (root.right.isCheck() == false) {
				if (!maincontrol.isEmpty()) {
					Iterator<String> itere = maincontrol.keySet().iterator();
					while (itere.hasNext()) {
						String key = itere.next();
						Stack<String> temp = new Stack<>();
						temp = maincontrol.get(key);
						if (temp.contains(root.right.value)) {
							maincontrol.get(key).add(root.value);

						}
					}

				}
				root.setCheck(false);
			}
		}

		return root;

	}
	public int number(Node root) {
		
		if(root==null) {
			return 0;
		}
		if(root.right==null&&root.left==null) {
			return 1;
		}
		
		return number(root.left)+number(root.right);
	}

	public ArrayList<Stack<String>> findCorruptChunks(String string) {
	
		Queue<String> newmeta = new LinkedList<>();
		Queue<String> newdata = new LinkedList<>();
		Stack<String> temp = new Stack<>();
		Stack<String> temp2 = new Stack<>();
		ArrayList<Stack<String>> boss = new ArrayList<>();
		System.out.println(number(this.root));

		try {

			File file = new File(string);
			Scanner concole = new Scanner(file);
			while (concole.hasNextLine()) {
				String line = concole.nextLine();
				if (!line.equals("")) {
					temp.add(line);
				}

			}
			concole.close();

			for (int i = 0; i < this.numchunk; i++) {

				String line = temp.pop();

				temp2.add(line);

			}
			while (!temp2.isEmpty()) {
				newmeta.add(temp2.pop());
			}

			for (String stack : this.chunk) {
				newdata.add(stack);

			}

			corruption(this.root, newdata, newmeta);
			Iterator<String> itere = this.maincontrol.keySet().iterator();
			while (itere.hasNext()) {
				Stack<String> newstack= new Stack<>();
				Stack<String> temp3= new Stack<>();
				temp3=this.maincontrol.get(itere.next());
				while(!temp3.isEmpty()) {
					newstack.add(temp3.pop());
				}
				boss.add(newstack);
			}

		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return boss;
	}

}
