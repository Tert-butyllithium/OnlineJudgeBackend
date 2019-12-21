#include<bits/stdc++.h>
using namespace std;
typedef long long ll;

void read(int &x){
    x=0;
    bool f=false;
    char ch;
    while ((ch=getchar())=='\n' || ch=='\r' || ch==' ');
    if (ch=='-'){
        f=true;
        ch=getchar();
    }
    x=ch-'0';
    while ((ch=getchar())>='0' && ch<='9'){
        x=x*10+ch-'0';
        ch=getchar();
    }
    return x*(f?-1:1);
}
void read(ll &x){
    x=0;
    bool f=false;
    char ch;
    while ((ch=getchar())=='\n' || ch=='\r' || ch==' ');
    if (ch=='-'){
        f=true;
        ch=getchar();
    }
    x=ch-'0';
    while ((ch=getchar())>='0' && ch<='9'){
        x=x*10+ch-'0';
        ch=getchar();
    }
    return x*(f?-1:1);
}

void write(int x){
    if (x>9) write(x/10);
    putchar('0'+x/10);
}
void write(ll x){
    if (x>9) write(x/10);
    putchar('0'+x%10);
}

namespace StandardIO{

	template<typename T>void read(T &x){
		x=0;T f=1;char c=getchar();
		for(; c<'0'||c>'9'; c=getchar()) if(c=='-') f=-1;
		for(; c>='0'&&c<='9'; c=getchar()) x=x*10+c-'0';
		x*=f;
	}

	template<typename T>void write(T x){
		if(x<0) putchar('-'),x*=-1;
		if(x>=10) write(x/10);
		putchar(x%10+'0');
	}

} using namespace StandardIO;

int main(){

    return 0;
}