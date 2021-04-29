#include<iostream>
using namespace std;

class CString
{
	int size;

	char *p;

public:
CString(int x): size(x), p(new char[size]) {}

};

int main(void)
{
    CString cs(5);
    (cs.p)[0] = 'A';
    cout << p[0] << endl;
    return 0;

}