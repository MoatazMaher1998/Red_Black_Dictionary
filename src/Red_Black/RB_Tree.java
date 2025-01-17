package Red_Black;

public class RB_Tree {
    public int  numberofwords=0;
	public Node root;

	public RB_Tree() {
		 root = Node.nil;

	}
	

	public void Insert(String data) {
		Node enter = new Node(data);
		Node x = root;
		if(root == Node.nil) {
			root= enter;
			root.color=Node.black;
			root.parent=Node.nil;
		}
		else {
			enter.color=Node.red;
		while(true) {
		if (enter.data.compareTo(x.data) < 0) {
            if (x.left == Node.nil) {
                x.left = enter;
                enter.parent = x;
                break;
            } else {
                x = x.left;
            }
        } else if (enter.data.compareTo(x.data) >= 0) {
            if (x.right == enter.nil) {
                x.right = enter;
                enter.parent = x;
                break;
            } else {
                x = x.right;
            }
        }
    }
	
		fix_insertion(enter);
		}
	}
	

	private void Right_Rotation(Node Parent) {
		Node y = Parent.left;
		Parent.left = y.right;
		if (y.right != Node.nil) {
			y.right.parent = Parent;
		}
		y.parent = Parent.parent;
		if (Parent.parent == Node.nil) {
			root = y;
		} else if (Parent == Parent.parent.right) {
			Parent.parent.right = y;
		} else {
			Parent.parent.left = y;

		}
		y.right = Parent;
		Parent.parent = y;

	}
	
	private void Left_Rotation( Node Parent) {
		Node y = Parent.right;
		Parent.right = y.left;
		if (y.left != Node.nil) {
			y.left.parent = Parent;
		}
		y.parent = Parent.parent;
		if (Parent.parent == Node.nil) {
			root = y;
		} else if (Parent == Parent.parent.left) {
			Parent.parent.left = y;
		} else {
			Parent.parent.right = y;

		}
		y.left = Parent;
		Parent.parent = y;

	}

	public void fix_insertion( Node enter) {
		while (enter.parent.color == Node.red) {
			Node uncle = Node.nil;
			if (enter.parent == enter.parent.parent.left) {
				uncle = enter.parent.parent.right;
				if (uncle != Node.nil && uncle.color == Node.red) {
					enter.parent.color = Node.black;
					uncle.color = Node.black;
					enter.parent.parent.color = Node.red;
					enter = enter.parent.parent;
					continue;
				} 
				
					if (enter == enter.parent.right) {
						enter = enter.parent;
                       Left_Rotation(enter);
						// ana right
						// leftt rotation

					}
					enter.parent.color = Node.black;
					enter.parent.parent.color = Node.red;
					Right_Rotation(enter.parent.parent);
					// right

				

			} else {
				uncle = enter.parent.parent.left;
				if (uncle != Node.nil && uncle.color == Node.red) {
					enter.parent.color = Node.black;
					uncle.color = Node.black;
					enter.parent.parent.color = Node.red;
					enter = enter.parent.parent;
					continue;
				} 
					if (enter == enter.parent.left) {

						enter = enter.parent;
						Right_Rotation(enter);
					}
					enter.parent.color = Node.black;
					enter.parent.parent.color = Node.red;
                    Left_Rotation(enter.parent.parent);
				}
			}
	        root.color=Node.black;
	}
	int UpdateTreeHeight(Node node)  {
		int left,right,height;
        if (node == Node.nil) {height=0;} 
        else {  
            left  = UpdateTreeHeight(node.left); 
            right = UpdateTreeHeight(node.right); 
            if (left > right) { height = left+1;} 
             else {height = right+1;} 
            
             } 
        return height;
    } 
	void deleteTree(){
		numberofwords = 0;
        root = Node.nil;
    }
	
	public boolean delete(Node z){
		
        if((z = findNode(z, root))==null)return false;
        Node x;
        Node y = z; // temporary reference y
        int y_original_color = y.color;
        
        if(z.left == Node.nil){
            x = z.right;  
            transplant(z, z.right);  
        }else if(z.right == Node.nil){
            x = z.left;
            transplant(z, z.left); 
        }else{
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; 
        }
        if(y_original_color==Node.black)
            deleteFixup(x);  
        return true;
    }
	
	public Node findNode(Node findNode, Node node) {
        if (root == Node.nil) {
            return null;
        }

        if (findNode.data.compareTo(node.data) < 0) {
            if (node.left != Node.nil) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.data.compareTo(node.data) > 0) {
            if (node.right != Node.nil) {
                return findNode(findNode, node.right);
            }
        } else if (findNode.data.equals(node.data) == true) {
            return node;
        }
        return null;
    }
	
	private void transplant(Node target, Node with){ 
        if(target.parent == Node.nil){
            root = with;
        }else if(target == target.parent.left){
            target.parent.left = with;
        }else
            target.parent.right = with;
        with.parent = target.parent;
  }


	private Node treeMinimum(Node subTreeRoot){
        while(subTreeRoot.left!=Node.nil){
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }
	
	
    void deleteFixup(Node x){

        while(x!=root && x.color == Node.black){ 
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(w.color == Node.red){
                    w.color = Node.black;
                    x.parent.color = Node.red;
                    Left_Rotation(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == Node.black && w.right.color == Node.black){
                    w.color = Node.red;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == Node.black){
                    w.left.color = Node.black;
                    w.color = Node.red;
                    Right_Rotation(w);
                    w = x.parent.right;
                }
                if(w.right.color == Node.red){
                    w.color = x.parent.color;
                    x.parent.color = Node.black;
                    w.right.color = Node.black;
                    Left_Rotation(x.parent);
                    x = root;
                }
            }else{
                Node w = x.parent.left;
                if(w.color == Node.red){
                    w.color = Node.black;
                    x.parent.color = Node.red;
                    Right_Rotation(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == Node.black && w.left.color == Node.black){
                    w.color = Node.red;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == Node.black){
                    w.right.color = Node.black;
                    w.color = Node.red;
                    Left_Rotation(w);
                    w = x.parent.left;
                }
                if(w.left.color == Node.red){
                    w.color = x.parent.color;
                    x.parent.color = Node.black;
                    w.left.color = Node.black;
                    Right_Rotation(x.parent);
                    x = root;
                }
            }
        }
        x.color = Node.black; 
    }

}
