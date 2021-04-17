    static class MyHashMap{
        int N=100003;
        int[]h;
        public MyHashMap(){
            h=new int[N];
            for(int i=0;i<N;i++) h[i]=Integer.MIN_VALUE;
        }
        public void insert(int x){
            int k=find(x);
            h[k]=x;
        }
        public boolean query(int x){
            int k=find(x);
            return h[k]==x;
        }
        //find函数有两个含义，如果x已经存在，则返回x的下标；如果x不存在，返回x应该存储的位置
        private int find(int x){
            int k=hash(x);
            //如果当前位置有人，并且不是x，就找下一个位置
            while(h[k]!=Integer.MIN_VALUE && h[k]!=x){
                k++;
                //如果k已经到最后，从0再开始循环查找
                if(k==N) k=0;
            }
            return k;
        }
        private int hash(int x){
            return (x%N+N)%N;
        }
    }