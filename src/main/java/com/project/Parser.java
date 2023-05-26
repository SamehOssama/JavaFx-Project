package com.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Parser {
	private static String getDocument() throws IOException {
		final String fileName = "Example.mdl";
		final String startLine = "__MWOPC_PART_BEGIN__ /simulink/systems/system_root.xml";
		Path filePath = Path.of("Project", "src", "main", "java", "com", "project", fileName);
		String file = Files.readString(filePath);
		int startLineIndex = file.indexOf(startLine);
		String document = file.substring(startLineIndex + startLine.length(), file.indexOf("</System>", startLineIndex) + 9); // Get the required part only
		document = document.substring(document.indexOf("<Block"), document.lastIndexOf("</System>"));
		// System.out.println(document);
		return document;
	}

	public static ArrayList<Block> getBlocks() throws Exception {
		String file = getDocument();

		String[] blockStart = { "BlockType=\"", "Name=\"", "SID=\"", "ZOrder\">", "Position\">" };
		String[] blockEnd = { "\"", "\"", "\"", "</P>", "</P>" };
		String[] inputBlockStart = { "Ports\">", "NumInputPorts\">", "Inputs\">" };
		String[] inputBlockEnd = { "</P>", "</P>", "</P>" };

		ArrayList<Block> blocks = new ArrayList<Block>();

			for (int i = file.indexOf("<Block"); i >= 0; i = file.indexOf("<Block", i + 1)) {
				int blockEndIndex = file.indexOf("</Block>", i);
				Block block = new Block();

				for (int j = 0; j < blockStart.length; j++) {

					int start = file.indexOf(blockStart[j], i) + blockStart[j].length();
					int end = file.indexOf(blockEnd[j], start);
					String value = file.substring(start, end);

					switch (blockStart[j]) {
						case "BlockType=\"":
							block.setBlockType(value);
							break;

						case "Name=\"":
							block.setName(value);
							break;

						case "SID=\"":
							block.setSid(Integer.parseInt(value));
							break;

						case "ZOrder\">":
							block.setzOrder(Integer.parseInt(value));
							break;

						case "Position\">":
							block.setPosition(value);
							break;

						default:
							throw new Exception("Wrong block data");
					}
				}

				if (block.getBlockType().equals("Sum") || block.getBlockType().equals("Scope")) {

					SumBlock inputBlock = new SumBlock(block);

					for (int j = 0; j < inputBlockStart.length; j++) {

						int start = file.indexOf(inputBlockStart[j], i) + inputBlockStart[j].length();
						int end = file.indexOf(inputBlockEnd[j], start);
						if(start > blockEndIndex || start < i) continue;
						String value = file.substring(start, end);

						switch (inputBlockStart[j]) {
							case "Ports\">":
								inputBlock.setPorts(value);
								break;

							case "NumInputPorts\">":
								inputBlock.setInputNumber(Integer.parseInt(value));
								break;

							case "Inputs\">":
								inputBlock.setInputNumber(value.length());
								break;
							default:
								throw new Exception("Wrong input block data");
						}
					}

					blocks.add(inputBlock);
				} else {
					blocks.add(block);
				}
			}

		return blocks;
	}

	public static ArrayList<MyLine> getLines() throws Exception {
		String file = getDocument();

			// Create a template for the array of container properties.
			String[] lineStart = { "ZOrder\">", "Src\">", "Points\">", "Dst\">" };
			String lineEnd = "</P>";
			String[] branchStart = { "ZOrder\">", "Points\">", "Dst\">" };
			String branchEnd = "</P>";

			ArrayList<MyLine> lines = new ArrayList<MyLine>();

			for (int i = file.indexOf("<Line>"); i >= 0; i = file.indexOf("<Line>", i + 1)) {
				int lineEndIndex = file.indexOf("</Line>", i);
				int branchStartIndex = file.indexOf("<Branch>", i);
				boolean hasBranch = false;
				if (branchStartIndex < lineEndIndex && branchStartIndex != -1) hasBranch= true;
				MyLine line = new MyLine();

				for (int j = 0; j < lineStart.length; j++) {
					int start = file.indexOf(lineStart[j], i) + lineStart[j].length();
					int end = file.indexOf(lineEnd, start);
					if (start > lineEndIndex) continue;
					if(hasBranch && lineStart[j].equals("Dst\">")) continue;
					String value = file.substring(start, end);

					switch (lineStart[j]) {
						case "ZOrder\">":
							line.setzOrder(Integer.parseInt(value));
							break;

						case "Src\">":
							line.setSource(value);
							break;

						case "Points\">":
							line.setPoints(value);
							break;

						case "Dst\">":
							line.setDestination(value);
							break;

						default:
							throw new Exception("Wrong line data");
					}
				}

				if (!hasBranch){    // Go to the next Line tag if there are no branches
					lines.add(line);
					continue;
				}

				ArrayList<Branch> branches = new ArrayList<Branch>();

				for (int j = file.indexOf("<Branch>", i); j >= 0; j = file.indexOf("<Branch>", j + 1)) {
					if(j > lineEndIndex) break;
					int branchEndIndex = file.indexOf("</Branch>", j);
					Branch branch = new Branch();

					for (int k = 0; k < branchStart.length; k++) {
						int start = file.indexOf(branchStart[k], j) + branchStart[k].length();
						int end = file.indexOf(branchEnd, start);
						if(start > branchEndIndex) continue;
						String value = file.substring(start, end);

						switch (branchStart[k]) {
							case "ZOrder\">":
								branch.setzOrder(Integer.parseInt(value));
								break;

							case "Points\">":
								branch.setPoints(value);
								break;

							case "Dst\">":
								branch.setDestination(value);
								break;

							default:
								throw new Exception("Wrong branch data");
						}

					}
					branches.add(branch);

				}
				Branch[] branchesArr = branches.toArray(new Branch[branches.size()]);
				line.setBranches(branchesArr);

				lines.add(line);
			}

		return lines;
	}

}
