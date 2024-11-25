#include <stdio.h>
#include <stdlib.h>

int grid[9][9];
int n=9;

void display(int grid[9][9],int n)
{
    int i,j;
    printf("\n The sudoku is: \n\n");
    for(i=0;i<n;i++)
    {
        if(i%3 == 0 ) printf("\n");
        for(j=0;j<n;j++)
        {   
            if(j%3 == 0 && j != 0)
            {
                printf("  %d",grid[i][j]);
            }
            else            
            {
                printf(" %d",grid[i][j]);
            }
            printf("");
        }
        printf(" \n");
    }
}

int issafe(int grid[9][9],int row,int col,int num)
{   
    for(int i=0;i<n;i++)
    {
        if(grid[row][i] == num || grid[i][col] == num)
            return 0;
    }

    int srow = row - row%3;
    int scol = col - col%3;

    for (int i = 0; i < 3; i++) 
    {
        for (int j = 0; j < 3; j++) 
        {
            if (grid[i + srow][j + scol] == num) 
            {
                return 0;
            }
        }
    }

    return 1;
}

int solve(int grid [9][9], int r,int c)
{
    if (r == n)
        return 1;
    
    if (c == n) {
        return solve(grid, r + 1, 0);
    }
    
    if (grid[r][c] > 0)
        return solve(grid, r, c + 1);
        
    for (int num = 1; num <= n; num++) 
    {
        if (issafe(grid, r, c, num)==1) 
        {
            grid[r][c] = num;
            if (solve(grid, r, c + 1)==1)
                return 1;
        }
        grid[r][c] = 0;
    }
    return 0;
}

void input(int grid[9][9])
{
    
}

int main()
{
    int grid[9][9] = {{2,9,6,0,0,1,3,4,0},{0,8,1,4,3,0,7,2,0},{7,4,0,0,0,6,8,0,0},{0,0,2,1,0,5,0,8,7},{6,0,0,2,0,7,0,0,0},{0,1,7,0,0,0,2,5,0},{1,2,0,6,0,0,5,0,8},{0,0,4,0,0,8,1,0,2},{0,6,5,0,0,2,4,0,0}};
    display(grid,n);
    if (solve(grid, 0, 0)==1)
        display(grid,n);
    else
        printf("No solution exists");

}