#include<iostream>
using namespace std;
#define STACK_SIZE 100

class Stack
{
    private:
    int top;
    int buffer[STACK_SIZE];
    public:
    Stack() {top =  -1 ;}
    bool push( int i );
    bool pop( int &i );
};

bool Stack::push( int i )
{
 if( top == STACK_SIZE - 1 )
    {
        cout << "stack is overflow!" << endl;
        return false;
    }
    else
    {
        top++;
        buffer[top] = i;
        return true;
    }

}
bool Stack::pop( int &i )
{
    if( top == -1 )
    {
        cout << "stack is empty" << endl;
        return false;
    }
    else
    {
        i = buffer[top];
        top--;
        return true;
    }
}

int main( void )
{
    Stack st1,st2;
    int x;
    st1.push(12);
    st1.pop(x);
    cout << x << endl;
    return 0;
}