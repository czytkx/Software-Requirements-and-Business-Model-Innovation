import java.io.IOException;
import java.io.ObjectInput;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixUDG // Undirected Graph
{
    private int mEdgNum; // number of all edges
    private char[] mVexs; // set of vertexes
    private int[][] mMatrix; //adjacent matrix
    private static final int INF = Integer.MAX_VALUE;
    //Create a graph( inputting data by yourself )
    public MatrixUDG()
    {
        System.out.println("Input vertex number: ");
        int vLen = readInt(); // num of vertexes
        System.out.println("Input edge number: ");
        int eLen = readInt();//num of edges used

        //Init vertexes
        mVexs = new char[ vLen ];
        for( int i = 0 ; i < vLen ; i++ )
        {
            System.out.printf("vertex( %d ): " , i );
            mVexs[i] = readChar();
        }
        //Init weight of edges
        mEdgNum = eLen;
        mMatrix = new int[ vLen ][ vLen ];
        for( int i = 0 ; i < vLen ; i++ )
        {
            for( int j = 0 ; j < vLen ; j++ )
            {
                mMatrix[i][j] = i == j ? 0 : INF;
            }
        }
        //Init weight of edges by users' input
        for( int i = 0 ; i < eLen ; i++ )
        {
            //reading the start vertex , end vertex and weight
            System.out.printf("Edge( %d )" , i );
            char c1 = readChar();// start v
            char c2 = readChar(); // end v
            int weight = readInt(); // weight

            int p1 = getPosition( c1 );
            int p2 = getPosition( c2 );
            if( p1 == -1 || p2 == -1 )
            {
                System.out.println("Input error! Invalid edge!");
                return;
            }
            mMatrix[p1][p2] = weight;
            mMatrix[p2][p1] = weight; // undirected graph
        }
    }
    /*
        Create a graph using given matrix
        @paragraph:
        vexs[] ------ set of vertexes
        matrix[][] -- adjacent matrix
     */
    public MatrixUDG( char[] vexs , int[][] matrix )
    {
        int vLen = vexs.length;
        //Init vertexes
        mVexs = new char[vLen];
        for( int i = 0 ; i < vLen ; i++ )
        {
            mVexs[i] = vexs[i];
        }
        //Init edges
        mMatrix = new int[vLen][vLen];
        for( int i = 0 ; i < vLen ; i++ )
        {
            for( int j = 0 ; j < vLen ; j++ )
            {
                mMatrix[i][j] = matrix[i][j];
            }
        }
        // get mEdgNum
        for( int i = 0 ; i < vLen ; i ++ )
        {
            for( int j = i + 1 ; j < vLen ; j++ )
                if( mMatrix[i][j] != INF ) mEdgNum++;
        }
    }
    // get position of ch
    private int getPosition( char ch )
    {
        for( int i = 0 ; i < mVexs.length ; i++ )
            if( mVexs[i] == ch ) return i;
        return -1;
    }
    // read an input char
    private char readChar()
    {
        char ch = '0';
        do {
            try {
                ch = (char) System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while( !( ( 'a' < ch && ch < 'z' ) || ( 'A' < ch && ch < 'Z' ) ) );

        return ch;
    }
    //read an input int
    private int readInt()
    {
        Scanner sc = new Scanner( System.in );
        return sc.nextInt();
    }
    // get index of the first adjacent vertex of v , return -1 if failed
    private int firstVertex( int v )
    {
        if( v < 0 || v > mVexs.length - 1 ) return -1;
        for( int i = 0 ; i < mVexs.length ; i++ )
            if( mMatrix[v][i] != 0 && mMatrix[v][i] != INF ) return i;
        return -1;
    }
    // get index of the  adjacent vertex of v compared to w , return -1 if failed
    private int nextVertex( int v , int w )
    {
        if ( v<0 || v>(mVexs.length-1) || w<0 || w>(mVexs.length-1))
            return -1;
        for( int i = w + 1 ; i < mVexs.length ; i++ )
            if( mMatrix[v][i] != 0 && mMatrix[v][i] != INF ) return i;
        return -1;


    }


    //DFS recursive
    private void DFS( int v , boolean[] visited )
    {
        visited[v] = true;
        System.out.printf("%c ", mVexs[v] );
        for( int w = firstVertex(v) ; w > 0 ; w = nextVertex( v, w ) )
            if( !visited[w] )  DFS( w , visited );
    }
    private void mDFS( int v , boolean[] visited )
    {
        visited[v] = true;
        System.out.printf("%c ", mVexs[v] );
        for( int w = firstVertex(v) ; w > 0 ; w = nextVertex( v , w ) )
        {
            if( !visited[w] ) mDFS( w , visited );
        }

    }
    public void DFS()
    {
        boolean[] visited = new boolean[ mVexs.length ];
        for( int i = 0 ; i < mVexs.length ; i++  ) visited[i] = false;
        System.out.printf("DFS: ");
        for( int i = 0 ; i < mVexs.length ; i++ )
        {
            if( !visited[i] ) DFS( i , visited);
        }

        System.out.printf("\n");
    }
    private void BFS ( int v , boolean[] visited )
    {
       int head = 0;
       int rear = 0;
       int[] queue = new int[ mVexs.length ];
       queue[rear++] = v; // enqueue
       visited[v] = true;
       while( head != rear )
       {
           int out = queue[ head++ ]; // dequeue
           System.out.printf("%c ", mVexs[v] );

           for( int w = firstVertex( v ) ; w > 0 ; w = nextVertex( v , w ) )
           {
               if( !visited[w] )
               {
                   queue[ rear++ ] = w;
                   visited[ w ] = true;
               }
           }
       }
    }

    private void mBFS( int v , boolean[] visited )
    {
        int head = 0;
        int rear = 0;
        int[] queue = new int [mVexs.length];
        queue[rear++] = v;
        visited[v] = true;
        while( head != rear )
        {
            v = queue[ head++ ];
            System.out.printf("%c ", mVexs[v] );

            for( int w = firstVertex( v ) ; w > 0; w = nextVertex( v , w ) )
            {
                if( !visited[w] )
                {
                    queue[rear++] = w;
                    visited[ w ] = true;
                }
            }
        }





    }
    public void BFS()
    {
        boolean[] visited = new boolean[ mVexs.length ];
        for( int i = 0 ; i < mVexs.length ; i++  ) visited[i] = false;
        System.out.printf("BFS: ");
        for( int i = 0 ; i < mVexs.length ; i++ )
        {
            if( !visited[i] ) BFS( i , visited);
        }

        System.out.printf("\n");
    }
    public void dijkstra( int v )
    {
        boolean[] known = new boolean[ mVexs.length ];
        int[] dist = new int[ mVexs.length ];//the cost from v to index
        int[] prev = new int[ mVexs.length ]; //the second last vertex from v to the dist vertex
        for( int i = 0 ; i < mVexs.length ; i++ )
        {
            known[i] = false;
            dist[i] = mMatrix[ v ][ i ];
            prev[i] = 0;
        }
        //  遍历 mVexs.length 次；每次找出一个顶点的最短路径。
        int k = 0;
        for( int i = 0 ; i < mVexs.length ; i++ )
        {
            int min = INF;// min will be the lowest weight to k
            for( int j = 0 ; j < mVexs.length ; j++ )
            {
                int temp = dist[j];
                if( !known[j] && min > temp )
                {
                    min = temp;
                    k = j;
                }
            }
            known[k] = true; // At first time , k must be v, because 0 is the minimum value in this graph

            //correct dist[i] and prev[i]
            for( int j = 0 ; j < mVexs.length ; j++ )
            {
                int temp = mMatrix[k][j] == INF ? INF : min + mMatrix[k][j];
                if( !known[j] && temp < dist[ j ]  )
                {
                    dist[j] = temp;
                    prev[j] = k;
                }
            }
        }
        // print the result of dijkstra
        System.out.printf("Dijkstra(%c): \n", mVexs[ v ] );
        for( int i = 0 ; i < mVexs.length ; i++ )
        {
            System.out.printf( "shortest( %c , %c ) = %c) \n", mVexs[v] , mVexs[i] , dist[i] );
        }
    }
    public void prim( int vs ) //Create minimum spanning tree
    {
        int vLen = mVexs.length;
        char[] prims = new char[vLen];// the result array
        int index = 0;//the index of prims
        int[] weight = new int[ vLen ];//weight in prims

        prims[ index++ ] = mVexs[ vs ];
        for( int i = 0 ; i < vLen ; i++ ) weight[ i ] = mMatrix[ vs ][ i ];
        //of course weight[vs] == 0
        for( int i = 0 ; i < vLen ; i++ )
        {
            if( vs == i ) continue;
            int j = 0;
            int k = 0;
            int min = INF;
            //find the vertex of minimum weight in all vertexes not yet added to spanning tree
            while( j < vLen )
            {
                if( weight[j] != 0 && weight[ j ] < min  )
                {
                    min = weight[ j ];
                    k = j;
                }
                j++;
            }
            prims[index++] = mVexs[ k ];
            //mark the weight of vertex k to show it's been added to spanning tree
            weight[k] = 0;
            //renew others' weight
            for(  j = 0 ; j < vLen ; j++ )
            { //only unadded need to be renewed
                if( weight[j] != 0 &&  mMatrix[k][j] < weight[j]  ) weight[ j ] = mMatrix[k][j];
            }
        }
        //compute the total weight of minimum spanning tree
        int sum = 0;
        for( int i = 1 ; i < vLen ; i++ ) // compute n - 1 times
        {
            int min = INF;
            int p = getPosition( prims[ i ] );
            for( int j = 0 ; j < i ; j++ )
            {
                int m = getPosition( prims[ j ] );
                if( mMatrix[m][p] < min  )
                    min = mMatrix[m][p];
            }
            sum += min;
        }
        //print minimum spanning tree
        System.out.printf( "PRIM(%d) =  %d \n", vs , sum );
        for( int i = 0 ; i < vLen ; i++  )
            System.out.printf( "%c ", prims[i] );

        System.out.printf( "\n" );
    }
    private static class Edge implements Comparable<Edge>
    {
        private char start;
        private char end;
        private int weight;
        public Edge( char start , char end , int weight )
        {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }



    public char getStart(){ return start; }
    public char getEnd(){ return end; }



        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    private ArrayList< Edge > Kruskal(Edge[] edges , int vLen)
    {
        DisjSets ds = new DisjSets( vLen );
        PriorityQueue< Edge > pq = new PriorityQueue< >( edges );
        List< Edge > result = new ArrayList<>();

        while( result.size() != vLen - 1 )
        {
            Edge e = pq.deleteMin();
            int rootStart = ds.find( e.getStart() );
            int rootEnd = ds.find( e.getEnd() );

            if( rootStart != rootEnd )
            {
                result.add(e);
                ds.union( rootStart, rootEnd );
            }
        }
        return (ArrayList)result;
    }
    public void Kruskal()
    {
        //TODO

    }
}

class DisjSets
{
    private int[] s;
    public DisjSets( int numElements )
    {
        s = new int[ numElements ];
        for( int i = 0 ; i < numElements ; i++  ) s[i] = -1;
    }
    public void union ( int root1 , int root2 )
    {
        if( s[root2] < s[root1]  ) //root2 is deeper
            s[root1] = root2;
        else
        {
            if( s[root1] == s[root2]   ) s[root1]--;
            s[ root2 ] = root1;
        }


    }
    public int find( int x )
    {
        if( s[x] < 0 ) return x;
        else return find( s[x] );
    }
}
class PriorityQueue < AnyType extends Comparable< ? super AnyType > > {
    private int currentSize;
    private AnyType[] array;

    //   public PriorityQueue() { }

    public PriorityQueue(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for (AnyType item : items) array[i++] = item;
        buildHeap();
    }

    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    private boolean isEmpty() {
        return currentSize == 0;
    }

    private AnyType findMin() {
        return array[1];
    }

    public AnyType deleteMin() {
        if (isEmpty()) System.out.println("Fail deletion！ No element!");
        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        return minItem;
    }

    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) child++;
            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else break;
        }
        array[hole] = tmp;
    }
}

