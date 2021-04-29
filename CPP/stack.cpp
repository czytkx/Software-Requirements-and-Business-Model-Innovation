#include<iostream>
using namespace std;
#define STACK_SIZE 100
struct Stack
{
    int top;
    int buffer[STACK_SIZE];
};

bool push( Stack &s , int i )
{
    if( s.top == STACK_SIZE - 1 )
    {
        cout << "stack is overflow!" << endl;
        return false;
    }
    else
    {
        s.top++;
        s.buffer[s.top] = i;
        return true;
    }
}

bool pop( Stack &s, int &i )
{
    if( s.top == -1 )
    {
        cout << "stack is empty" << endl;
        return false;
    }
    else
    {
        i = s.buffer[s.top];
        s.top--;
        return true;
    }
}

int main()
{
    Stack st1,st2;
    st1.top = -1;
    st2.top = -1;
    int x;
    push( st1,12 );
    pop( st1,x );
    cout << x << endl;
    return 0;
}