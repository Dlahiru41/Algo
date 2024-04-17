import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathFinder {

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static String findShortestPath(char[][] grid) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        String[] direction = {"up", "down", "left", "right"};
        int n = grid.length;

        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        Point[][] parent = new Point[n][n];
        String[][] directionAtStep = new String[n][n];

        // Find the starting point
        Point start = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start = new Point(i, j);
                    break;
                }
            }
        }

        queue.offer(start);
        visited[start.x][start.y] = true;

        // BFS traversal
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.x;
            int y = current.y;

            // Check if we reached the destination
            if (grid[x][y] == 'F') {
                StringBuilder path = new StringBuilder();
                Point node = current;
                while (node != null) {
                    if (parent[node.x][node.y] != null) {
                        int px = parent[node.x][node.y].x;
                        int py = parent[node.x][node.y].y;
                        for (int i = 0; i < 4; i++) {
                            if (px + dx[i] == node.x && py + dy[i] == node.y) {
                                directionAtStep[node.x][node.y] = direction[i];
                                break;
                            }
                        }
                        path.insert(0, String.format("%d. Move %s to (%d,%d)\n", queue.size() + 1, directionAtStep[node.x][node.y], node.x + 1, node.y + 1));
                    }
                    node = parent[node.x][node.y];
                }
                return path.toString();
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] != '0' && !visited[nx][ny]) {
                    queue.offer(new Point(nx, ny));
                    visited[nx][ny] = true;
                    parent[nx][ny] = current;
                }
            }
        }

        // If no path found
        return "No path found from S to F";
    }

    public static void main(String[] args)  {
        //grid is read from a text file
        try{
            BufferedReader br = new BufferedReader(new FileReader("grid.txt"));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        char[][] grid = {
                {'.', '.', '.', '.', '.', '0', '.', '.', '.', 'S'},
                {'.', '.', '.', '.', '0', '.', '.', '.', '.', '.'},
                {'0', '.', '.', '.', '.', '.', '0', '.', '.', '0'},
                {'.', '.', '.', '0', '.', '.', '.', '.', '0', '.'},
                {'.', 'F', '.', '.', '.', '.', '.', '.', '.', '0'},
                {'.', '0', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '0', '.', '.'},
                {'.', '0', '.', '0', '.', '.', '0', '.', '.', '0'},
                {'0', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '0', '0', '.', '.', '.', '.', '0', '.', '.'}
        };

        String shortestPath = findShortestPath(grid);
        System.out.println(shortestPath);
    }
}
