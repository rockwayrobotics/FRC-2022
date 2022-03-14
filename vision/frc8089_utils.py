'''Utility routines for development Python code for FRC team 8089.'''


def _import(name):
    import importlib

    try:
        mod = importlib.__import__(name)
    except ImportError:
        import os
        os.system(f'pip install {name}')

        mod = importlib.__import__(name)

    return mod
