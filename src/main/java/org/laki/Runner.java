package org.laki;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class Runner {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        int[][] initialData = initializeData();
        List<List<Entity>> finalLongestPaths = new ArrayList<>();
        for (int i = 0; i < initialData.length; i++) {
            for (int j = 0; j < initialData[i].length; j++) {
                List<List<Entity>> longestPaths = new ArrayList<>();
                List<Entity> path = new ArrayList<>();
                path.add(new Entity(i, j, initialData[i][j], Character.MIN_VALUE));
                longestPaths.add(path);
                finalLongestPaths.addAll(searchLongestPaths(longestPaths, initialData));
            }
        }

        Collections.sort(finalLongestPaths, Comparator.comparingInt(List::size));

        List<Entity> longestPath = finalLongestPaths.get(finalLongestPaths.size()-1);
        System.out.println("length : " + longestPath.size() + " drop : " +
                    (longestPath.get(longestPath.size() - 1).getValue() - longestPath.get(0).getValue()));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time taken to run program (ms): " + duration/1000000);
    }


    public static List<List<Entity>> searchLongestPaths(List<List<Entity>> longestPaths, int[][] initialData) {
        boolean havePossibleLongestPaths = true;
        while (havePossibleLongestPaths) {
            List<List<Entity>> possibleLongestPaths = new ArrayList<>();
            List<List<Entity>> newPossibleLongestPaths = new ArrayList<>();
            for (List<Entity> longestPath : longestPaths) {
                possibleLongestPaths = new ArrayList(longestPaths);
                Entity lastCell = longestPath.get(longestPath.size() - 1);
                //west check
                if (lastCell.getX() - 1 >= 0 && lastCell.cameFrom != 'W' &&
                        initialData[lastCell.getX() - 1][lastCell.getY()] > lastCell.getValue()) {
                    Entity newLastCell = new Entity(lastCell.getX() - 1, lastCell.getY(),
                            initialData[lastCell.getX() - 1][lastCell.getY()], 'E');
                    List<Entity> newLongestPath = new ArrayList<>(longestPath);
                    newLongestPath.add(newLastCell);
                    newPossibleLongestPaths.add(newLongestPath);
                }

                //north check
                if (lastCell.getY() - 1 >= 0 && lastCell.cameFrom != 'N' &&
                        initialData[lastCell.getX()][lastCell.getY() - 1] > lastCell.getValue()) {
                    Entity newLastCell = new Entity(lastCell.getX(), lastCell.getY() - 1,
                            initialData[lastCell.getX()][lastCell.getY() - 1], 'S');
                    List<Entity> newLongestPath = new ArrayList<>(longestPath);
                    newLongestPath.add(newLastCell);
                    newPossibleLongestPaths.add(newLongestPath);
                }
                //east check
                if (lastCell.getX() + 1 < initialData[lastCell.getX()].length && lastCell.cameFrom != 'E' &&
                        initialData[lastCell.getX() + 1][lastCell.getY()] > lastCell.getValue()) {
                    Entity newLastCell = new Entity(lastCell.getX() + 1, lastCell.getY(),
                            initialData[lastCell.getX() + 1][lastCell.getY()], 'W');
                    List<Entity> newLongestPath = new ArrayList<>(longestPath);
                    newLongestPath.add(newLastCell);
                    newPossibleLongestPaths.add(newLongestPath);
                }

                //south check
                if (lastCell.getY() + 1 < initialData[lastCell.getX()].length && lastCell.cameFrom != 'S' &&
                        initialData[lastCell.getX()][lastCell.getY() + 1] > lastCell.getValue()) {
                    Entity newLastCell = new Entity(lastCell.getX(), lastCell.getY() + 1,
                            initialData[lastCell.getX()][lastCell.getY() + 1], 'N');
                    List<Entity> newLongestPath = new ArrayList<>(longestPath);
                    newLongestPath.add(newLastCell);
                    newPossibleLongestPaths.add(newLongestPath);
                }
            }

            if (newPossibleLongestPaths.size() > 0) {
                longestPaths.addAll(newPossibleLongestPaths);
                if (newPossibleLongestPaths.get(0).size() > possibleLongestPaths.get(0).size()) {
                    longestPaths.removeAll(possibleLongestPaths);
                }
            } else {
                havePossibleLongestPaths = false;
            }

        }
        return longestPaths;
    }


    public static int[][] initializeData() throws IOException {
        int[][] initialData;
        try (Stream<String> stream = Files.lines(Paths.get("map.txt"))) {
            Iterator<String> lines = stream.iterator();
            AtomicInteger counter = new AtomicInteger(0);
            initialData = handleFirst(lines.next());
            int[][] finalInitialData = initialData;
            lines.forEachRemaining(line -> {
                int[] lineNumbers = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                finalInitialData[counter.get()] = lineNumbers;
                counter.getAndIncrement();
            });
            return transposeMatrix(finalInitialData);
        }
    }

    public static int[][] transposeMatrix(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    public static int[][] handleFirst(String firstLine) {
        String[] size = firstLine.split(" ");
        return new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
    }
}
